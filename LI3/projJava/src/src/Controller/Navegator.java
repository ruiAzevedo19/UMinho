package Controller;

import java.util.*;
import java.util.stream.Collectors;

public class Navegator implements INavegator {
    /* --- Variaveis de instancia ----------------------------------------------------------------------------------- */
    private final List<List<Object>> data;
    private List<Object> title;
    private Object titleName;
    private final boolean hasTitle;
    private final String[] topHeaders;
    private String[] sideHeaders;
    private final int maxPage;
    private final int pageSize;
    private int currentPage;
    private final int size;
    private boolean total;
    /* -------------------------------------------------------------------------------------------------------------- */

    public Navegator(int pageSize, String[] topHeaders, String[] sideHeaders, List<List<Object>> data){
        this.hasTitle = false;
        this.pageSize = pageSize;
        this.data = data;
        this.topHeaders = topHeaders;
        this.sideHeaders = sideHeaders;
        this.currentPage = 1;
        this.maxPage = (data.get(0).size() / pageSize) + 1;
        this.total = false;
        this.size = data.get(0).size();
    }

    public Navegator(int pageSize, Object title, String[] topHeaders, String[] sideHeaders, Collection<AbstractMap.SimpleEntry<Object,Object[][]>> tuples){
        this.pageSize = pageSize;
        this.title = tuples.stream().map(Map.Entry::getKey).map(o -> title + ": " + o).collect(Collectors.toList());
        this.titleName = title;
        this.hasTitle = true;
        this.data = new ArrayList<>();
        for(int i = 0; i < 12; i++)
            data.add(new ArrayList<>());
        for(Map.Entry<Object, Object[][]> e : tuples)
            for(int i = 0; i < e.getValue().length; i++)
                for(int j = 0; j < 12; j++)
                    data.get(j).add(e.getValue()[i][j]);
        this.topHeaders = topHeaders;
        this.sideHeaders = sideHeaders;
        this.currentPage = 1;
        this.maxPage = (data.get(0).size() / pageSize) + 1;
        this.total = false;
        this.size = data.get(0).size();
    }

    public Navegator(int pageSize, String[] topHeaders, List<List<Object>> data){
        this.hasTitle = false;
        this.pageSize = pageSize;
        this.data = data;
        this.topHeaders = topHeaders;
        this.currentPage = 1;
        this.total = false;
        this.maxPage = (data.get(0).size() % pageSize == 0) ? data.get(0).size() : (data.get(0).size() / pageSize) + 1;
        this.size = data.get(0).size();
    }

    /* --- Setters -------------------------------------------------------------------------------------------------- */

    public void setTotal(){
        total = true;
    }

    /* --- Getters -------------------------------------------------------------------------------------------------- */

    public String[] getTopHeaders() {
        return topHeaders;
    }

    public boolean isHasTitle() {
        return hasTitle;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getSize() {
        return size;
    }

    public boolean isTotal() {
        return total;
    }

    public Object getTitle(){
        return title == null ? null : title.get(currentPage - 1);
    }

    /* --- Functionality -------------------------------------------------------------------------------------------- */

    public Object[][] convert(){
        Object[][] d;
        int normPage = Math.max(0, Math.min(currentPage,maxPage));
        currentPage = normPage;
        int dx = (normPage - 1) * pageSize;

        if( currentPage == maxPage )
            d = new Object[size % pageSize][topHeaders.length];
        else
            d = new Object[pageSize][topHeaders.length];

        int i, j;

        for(i = 0; i < pageSize && dx < size; i++, dx++){
            j = sideHeaders == null ? 0 : 1;
            if( sideHeaders != null )
                d[i][0] = sideHeaders[i];
            for(List<Object> list : data)
                d[i][j++] = list.get(dx);
        }
        return d;
    }

    public boolean getPage(String action){
        boolean ok = true;

        switch (action){
            case "A" :
            case "a" : currentPage = Math.max(1, Math.min(currentPage - 1, maxPage));
                break;
            case "d" :
            case "D" : currentPage = Math.max(1, Math.min(currentPage + 1, maxPage));
                break;
            default  :  if (action.charAt(0) == '/')
                currentPage = find(action.substring(1));
            else
                try {
                    currentPage = Math.max(1, Math.min(Integer.parseInt(action), maxPage));
                } catch (NumberFormatException n) {
                    ok = false;
                }
        }
        return ok;
    }

    private int contains(int field, Object value){
        if( field == 0 && hasTitle )
            return title.indexOf(titleName + ": " + value) * pageSize;
        else {
            if( field > 0) {
                List<Object> elems = data.get(field - 1);
                if (elems.contains(value))
                    return elems.indexOf(value);
            }
        }
        return -1;
    }

    private int find(String exp){
        int found = currentPage;
        String pattern = "\\s*\\d+\\s*:\\s*\\w+(,\\d+)?\\s*";
        String[] p;

        int field;
        int dx;

        if( exp.matches(pattern) ){
            p = exp.split(":");
            field = Integer.parseInt(p[0]);
            if( field >= 0 && field <= topHeaders.length ) {
                dx = contains(field, p[1]);
                if( dx != -1)
                    found = dx / pageSize + 1;
            }
        }
        return found;
    }
}
