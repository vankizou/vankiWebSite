import com.alibaba.fastjson.JSON;
import com.zoufanqi.entity.Note;
import com.zoufanqi.entity.NoteContent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by vanki on 16/10/26.
 */
public class T {
    public static void main(String[] args) {
        String str = null;

        List<Note> list = JSON.parseArray(str, Note.class);
    }
}
