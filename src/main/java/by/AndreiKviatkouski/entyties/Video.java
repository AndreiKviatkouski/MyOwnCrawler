package by.AndreiKviatkouski.entyties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Video {

    private String url;
    private String name;
    private String downloadLink;

    public Video(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public Video(String url, String name, String downloadLink) {
        this.url = url;
        this.name = name;
        this.downloadLink = downloadLink;
    }
}
