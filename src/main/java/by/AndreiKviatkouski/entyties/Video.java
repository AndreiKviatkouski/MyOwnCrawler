package by.AndreiKviatkouski.entyties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    private String url;
    private String name;


    public static final Comparator<Video> compare = new Comparator<Video>() {
        @Override
        public int compare(Video el1, Video el2) {
            return (el1.getName().compareToIgnoreCase(el2.getName()));
        }
    };
}
