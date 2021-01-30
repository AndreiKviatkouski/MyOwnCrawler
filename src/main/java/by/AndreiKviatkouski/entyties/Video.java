package by.AndreiKviatkouski.entyties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Element;

import java.util.Comparator;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    private String url;
    private String name;
}
