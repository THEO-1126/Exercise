package cn.edu.guet;
import java.util.LinkedHashMap;
import java.util.Map;

// 统计单词个数
public class WordCount {
    public static void main(String[] args) {
        String sentence="this is a test, and that is also a test!";
        String[] words =sentence.split("\\s|, |!|\\.");
        System.out.println(words.length);
        Map<String,Integer> WordMap=new LinkedHashMap<String,Integer>();
        for (String str : words) {
            if (WordMap.containsKey(str)) {
                int num = WordMap.get(str) + 1;
                WordMap.put(str, num);
            } else {
                WordMap.put(str, 1);
            }
        }
        for (String key : WordMap.keySet()) {
            System.out.println(key+":"+WordMap.get(key));
        }
    }
}