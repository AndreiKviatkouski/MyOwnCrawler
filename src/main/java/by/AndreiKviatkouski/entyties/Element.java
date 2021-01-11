package by.AndreiKviatkouski.entyties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Element {

    private String url;
    private String param1;
    private Integer value1;
    private String param2;
    private Integer value2;
    private String param3;
    private Integer value3;
    private String param4;
    private Integer value4;
    private String param5;
    private Integer value5;

    public static final Comparator<Element> compare = new Comparator<Element>() {
        @Override
        public int compare(Element el1, Element el2) {
            return Long.compare(el1.value5, el2.value5);
        }
    };
}
