package com.zoufanqi.utils;

import com.zoufanqi.entity.AuthRoleOperation;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 权限中角色与操作相关的工具类
 * Created by vanki on 2017/5/9.
 */
public class AuthRoleOperationUtil {
    private static final Pattern REG_NUMBER = Pattern.compile("^\\d+$");
    private static final String isInitedFlag = "#isInited#";  // 角色可以什么操作都没有，所以避免重复查库标记
    private static final String urlPartOperationFlag = "#O#";  // 该操作权限。1添加，2更新，4删除，8查看。复合权限数值相加
    private static final int urlPartOperationDEFAULT = 0;   // 操作权限为null的时候的值

    // 操作类型，添加新的则需添加对应的操作
    private static final String urlPartOperationType_normalFlag = "#TN#";    // 普通
    private static final String urlPartOperationType_limitResourceOrigin = "#TR#";    // 限定资源所属

    private static final String urlPartReg_all = "*";   // 路径支持的正则符号，所有

    public static void main(String[] args) {

        System.out.println(15 & 8);

        AuthRoleOperation a1 = new AuthRoleOperation();
        a1.setContent("/*");
        a1.setOperation(14);

        AuthRoleOperation a2 = new AuthRoleOperation();
        a2.setContent("/a/*");
        a2.setOperation(1);

        AuthRoleOperation a3 = new AuthRoleOperation();
        a3.setContent("2,1");
        a3.setType(2);

        AuthRoleOperation a4 = new AuthRoleOperation();
        a4.setContent("3,1,  23");
        a4.setType(2);

        List<AuthRoleOperation> list = new ArrayList<>();
        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);
        Map<String, Object> map = buildRoleOperationMap(list);
        System.out.println(map);

        System.out.println(isAuthPass(map, "/a/b", 1, 1));
    }

    public static boolean isRoleOperationInited(Map<String, Object> roleOperationMap) {
        if (roleOperationMap == null) return false;
        return roleOperationMap.containsKey(isInitedFlag);
    }

    /**
     * 验证权限
     *
     * @param roleOperationMap
     * @param bestMatchingPatternUrl
     * @param urlOperation
     * @param urlOrigin              资源所属，资源表中定义
     *
     * @return
     */
    public static boolean isAuthPass(Map<String, Object> roleOperationMap, String bestMatchingPatternUrl, Integer urlOperation, Integer urlOrigin) {
        List<String> partList = getUrlPartList(bestMatchingPatternUrl);
        return isAuthPass(roleOperationMap, partList, urlOperation, urlOrigin);
    }

    public static boolean isAuthPass(Map<String, Object> roleOperationMap, List<String> urlPartList, Integer urlOperation, Integer urlOrigin) {
        if (urlOperation == null) return false;  // url还未指定操作权限
        if (urlOperation == urlPartOperationDEFAULT) return true;   // 该url无需权限
        if (!isRoleOperationInited(roleOperationMap)) return false;

        boolean flag;
        // 限定资源所属校验
        flag = doAuthLimitResourceOrigin(roleOperationMap, urlOrigin);
        if (!flag) return false;
        // 普通操作类型校验
        flag = doAuthNormalType(roleOperationMap, urlPartList, urlOperation);

        // TODO... 添加操作类型

        return flag;
    }

    private static boolean doAuthLimitResourceOrigin(Map<String, Object> roleOperationMap, Integer urlOrigin) {
        if (urlOrigin == null) return false;    // 未知url
        Set<Integer> resourceOriginSet = (Set<Integer>) roleOperationMap.get(urlPartOperationType_limitResourceOrigin);
        if (resourceOriginSet == null || resourceOriginSet.isEmpty()) return true;  // 无值表示不限定
        if (resourceOriginSet.contains(urlOrigin)) return true;
        return false;
    }

    /**
     * 普通操作类型权限校验
     * 以设置的最下级权限为准，如
     * /user/*      - 15
     * /user/a/b    - 8
     * 若请求url: /user/a/b，则权限为8
     * 若请求url: /user/a/b/c，则权限为15
     *
     * @param roleOperationMap
     * @param partList
     * @param urlOperation
     *
     * @return
     */
    private static boolean doAuthNormalType(Map<String, Object> roleOperationMap, List<String> partList, Integer urlOperation) {
        Integer[] operaArr = getOperation(roleOperationMap, partList);
        int len;
        if (operaArr == null || (len = operaArr.length) == 0) return false;

        Integer operationAll = operaArr[0];
        Integer operationSelf = null;
        if (len > 1) operationSelf = operaArr[1];

        operationSelf = operationSelf == null ? operationAll : operationSelf;
        if (operationSelf == null || operationSelf == urlPartOperationDEFAULT) return false;    // 无操作权限

        if ((operationSelf & urlOperation) == urlOperation) return true;   // 权限匹配

        return false;
    }

    /**
     * 获取角色对应该url的操作权限
     *
     * @param roleOperationMap
     * @param partList
     *
     * @return
     */
    private static Integer[] getOperation(Map<String, Object> roleOperationMap, List<String> partList) {
        roleOperationMap = (Map<String, Object>) roleOperationMap.get(urlPartOperationType_normalFlag);
        if (roleOperationMap == null || roleOperationMap.isEmpty()) return null;

        Integer operationAll = null;    // 父设置了 *
        Object operationAllCurr;
        Map<String, Object> operationAllCurrMap;

        Integer operationSelf = null;   // 当前url的权限
        Object operationSelfCurr;       // 当前url的权限

        for (int i = 0, size = partList.size(); i < size; i++) {
            String part = partList.get(i);

            /**
             * 父节点的 *
             */
            operationAllCurrMap = (Map<String, Object>) roleOperationMap.get(urlPartReg_all);
            if (operationAllCurrMap != null) {
                operationAllCurr = operationAllCurrMap.get(urlPartOperationFlag);
                if (operationAllCurr != null) operationAll = (Integer) operationAllCurr;
            }

            roleOperationMap = (Map<String, Object>) roleOperationMap.get(part);
            if (roleOperationMap == null || roleOperationMap.isEmpty()) break;

            if (i == (size - 1)) {  // 最后一个才是自己的权限
                operationSelfCurr = roleOperationMap.get(urlPartOperationFlag);
                if (operationSelfCurr == null) continue;
                operationSelf = (Integer) operationSelfCurr;
            }
        }
        return new Integer[]{operationAll, operationSelf};
    }

    /**
     * 构建角色对应的权限Map，如：
     * /a/b - 15(权限)
     * /a/c - 8
     * {#TN#:{#I#:true, {a:{b:{#O#:15}}, {c:{#O#:8}}}}}
     *
     * @param authRoleOperations
     *
     * @return
     */
    public static Map<String, Object> buildRoleOperationMap(List<AuthRoleOperation> authRoleOperations) {
        Map<String, Object> roleOperationMap = new HashMap<>();
        roleOperationMap.put(isInitedFlag, true);    // 初始化标记，若该角色未有值则确保不会一直去查库
        if (authRoleOperations == null || authRoleOperations.isEmpty()) return roleOperationMap;

        for (AuthRoleOperation operation : authRoleOperations) {
            String url = operation.getContent();
            Integer ope = operation.getOperation() == null ? urlPartOperationDEFAULT : operation.getOperation();
            Integer type = operation.getType();

            if (url == null || url.isEmpty() || ope == null) continue;
            addDataForRoleOperationMap(roleOperationMap, type, url, ope);
        }
        return roleOperationMap;
    }

    /**
     * 对单个url操作
     *
     * @param roleOperationMap
     * @param type             操作类型
     * @param content
     * @param operation        操作权限。1添加，2更新，4删除，8查询。
     */
    private static void addDataForRoleOperationMap(Map<String, Object> roleOperationMap, Integer type, String content, Integer operation) {
        if (roleOperationMap == null || content == null || operation == null) return;

        /**
         * 操作类型。1普通，2限定资源origin
         */
        if (type == null || type == 1) {
            addDataOperationTypeNormal(roleOperationMap, content, operation);
        } else if (type == 2) {
            addDataOperationTypeResourceOrigin(roleOperationMap, content);
        }
        // TODO... 添加操作类型
    }

    /**
     * 2.限定资源所属操作类型
     *
     * @param roleOperationMap
     * @param content
     */
    private static void addDataOperationTypeResourceOrigin(Map<String, Object> roleOperationMap, String content) {
        Set<Integer> resourceOriginSet = getResourceOriginPartList(content);
        if (resourceOriginSet == null || resourceOriginSet.isEmpty()) return;

        Set<Integer> resourceOriginCacheSet = (Set<Integer>) roleOperationMap.get(urlPartOperationType_limitResourceOrigin);
        if (resourceOriginCacheSet == null) {
            roleOperationMap.put(urlPartOperationType_limitResourceOrigin, resourceOriginSet);
        } else {
            resourceOriginCacheSet.addAll(resourceOriginSet);
        }
    }

    /**
     * 1.普通操作类型
     *
     * @param roleOperationMap
     * @param url              如：1./user/*，2./user/a/b.json
     * @param operation
     */
    private static void addDataOperationTypeNormal(Map<String, Object> roleOperationMap, String url, Integer operation) {
        Map<String, Object> map = (Map<String, Object>) roleOperationMap.get(urlPartOperationType_normalFlag);
        if (map == null) {
            map = new HashMap<>();
            roleOperationMap.put(urlPartOperationType_normalFlag, map);
        }
        roleOperationMap = map;

        /**
         * 构建对应操作类型的url的part模型，如：
         * /a/b - o:8
         * {a: {b: {#O#: 8}}}
         */
        List<String> partList = getUrlPartList(url);

        Map<String, Object> currMap = roleOperationMap;    // map索引
        Map<String, Object> nextMap;

        int partSize;
        if ((partSize = partList.size()) == 0) {    // 只当为根的情况
            currMap.put(urlPartOperationFlag, operation);
            return;
        }

        for (int i = 0; i < partSize; i++) {
            String urlPart = partList.get(i);
            nextMap = (Map<String, Object>) currMap.get(urlPart);

            if (nextMap == null) {
                nextMap = new HashMap<>();
                if (i == (partSize - 1)) {
                    nextMap.put(urlPartOperationFlag, operation);   // 如果是最后一段，则加上操作权限
                }

                currMap.put(urlPart, nextMap);
            } else if (i == (partSize - 1)) {
                nextMap.put(urlPartOperationFlag, operation);
            }
            currMap = nextMap;
        }
    }

    /**
     * jdk自带split太坑
     *
     * @param content
     */
    public static Set<Integer> getResourceOriginPartList(String content) {
        if (content == null || (content = content.trim()).isEmpty()) return null;

        Set<Integer> partSet = new HashSet<>();
        splitRecursion(partSet, content, ',');
        return partSet;
    }

    /**
     * 递归用
     *
     * @param partResultSet 结果, 1.过滤了为空的
     * @param content       要分割的字符串
     * @param splitChar     分割符
     */
    private static void splitRecursion(Set<Integer> partResultSet, String content, final char splitChar) {
        if ((partResultSet == null && partResultSet == null) || content == null) return;
        int offset = content.indexOf(splitChar);
        if (offset < 0) {
            if (content != null && !content.isEmpty() && REG_NUMBER.matcher(content = content.trim()).find()) {
                partResultSet.add(Integer.valueOf(content));
            }
            return;
        }
        String beforeDelimiter = content.substring(0, offset);
        String afterDelimiter = content.substring(offset + 1);

        if (beforeDelimiter != null && beforeDelimiter.length() > 0) {
            beforeDelimiter = beforeDelimiter.trim();
            if (REG_NUMBER.matcher(beforeDelimiter).find())
                partResultSet.add(Integer.valueOf(beforeDelimiter.trim()));
        }
        splitRecursion(partResultSet, afterDelimiter, splitChar);
    }

    /**
     * jdk自带split太坑
     *
     * @param url
     */
    public static List<String> getUrlPartList(String url) {
        List<String> partList = new ArrayList<>();
        if (url == null || (url = url.trim()).isEmpty()) return partList;
        final char urlSplit = '/';

        if (url.equals(urlSplit)) {
            partList.add(String.valueOf(urlSplit));
            return partList;
        }
        splitRecursion(partList, url, urlSplit);
        return partList;
    }

    /**
     * 递归用
     *
     * @param partResultList 结果, 1.过滤了为空的, 2.去除空隔, 3.字符全部转换为小写
     * @param url            要分割的字符串
     * @param splitChar      分割符
     */
    private static void splitRecursion(List<String> partResultList, String url, final char splitChar) {
        if (partResultList == null || url == null) return;
        int offset = url.indexOf(splitChar);
        if (offset < 0) {
            if (url != null && !url.isEmpty()) partResultList.add(url);
            return;
        }
        String beforeDelimiter = url.substring(0, offset);
        String afterDelimiter = url.substring(offset + 1);

        if (beforeDelimiter != null && beforeDelimiter.length() > 0) {
            partResultList.add(beforeDelimiter.trim().toLowerCase());
        }
        splitRecursion(partResultList, afterDelimiter, splitChar);
    }
}
