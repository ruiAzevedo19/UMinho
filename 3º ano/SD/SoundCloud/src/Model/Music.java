package Model;

import java.util.ArrayList;
import java.util.List;

public class Music {
    private int id;
    private String title;
    private String artist;
    private String path;
    private int year;
    private int downloads;
    private List<String> tags;

    /**
     * Método construtor
     *
     * @param title  : titulo da musica
     * @param artist : artista da musica
     * @param year   : ano de lancamento da musica
     */
    public Music(String title, String artist, int year, int downloads) {
        this.id = 0;
        this.title = title;
        this.artist = artist;
        this.path = null;
        this.year = year;
        this.downloads = downloads;
        this.tags = new ArrayList<>();
    }

    /**
     * Metodo construtor por copia
     *
     * @param music
     */
    public Music(Music music){
        this.id = music.getId();
        this.title = music.gettitle();
        this.artist = music.getartist();
        this.path = music.getPath();
        this.year = music.getyear();
        this.downloads = music.getDownloads();
        this.tags = new ArrayList<>(music.getTags());
    }

    /**
     * Metodo clone
     *
     * @return : copia da musica
     */
    public Music clone(){
        return new Music(this);
    }

    /**
     * Adiciona uma etiqueta a lista de tags
     *
     * @param tag : etiqueta a adicionar
     */
    public void adicionaEtiqueta(String tag){
        this.tags.add(tag);
    }

    /**
     * Adiciona uma lista de tags as tags ja existentes
     *
     * @param tags : tags a adicionar
     */
    public void adicionaListatags(List<String> tags){
        tags.stream().map(e -> this.tags.add(e));
    }

    /**
     * @return : identificador da musica
     */
    public int getId() {
        return id;
    }

    /**
     * @return : titulo da musica
     */
    public String gettitle() {
        return title;
    }

    /**
     * @return : artista da musica
     */
    public String getartist() {
        return artist;
    }

    /**
     * @return : ano de lancamento da musica
     */
    public int getyear() {
        return year;
    }

    /**
     * @return : numero de downloads da musica
     */
    public int getDownloads() {
        return downloads;
    }

    /**
     * @return : tags da musica
     */
    public List<String> getTags() {
        return new ArrayList<>(tags);
    }

    public String getPath() {
        return path;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setPath(String path){
        this.path = path;
    }

    public void incrementDownload(){
        this.downloads++;
    }
    /**
     *
     * @param tag : tag a adicionar
     */
    public void addTag(String tag){
        this.tags.add(tag);
    }

    /**
     * @return : dados de uma musica convertidos para uma String
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.id + ";");
        sb.append(this.title + ";");
        sb.append(this.artist + ";");
        sb.append(this.year + ";");
        sb.append(this.downloads + ";");
        for(String tag : this.tags) sb.append(tag + ";");
        sb.setLength(sb.length() - 1);

        return sb.toString();
    }
}
