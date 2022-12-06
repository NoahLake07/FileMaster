import FFM.Search;
import FFM.FileMaster;

public class Main extends FileMaster {

    private void test(){
        createDirectory("src/new folder/");
        createDirectory("src/new folder/sub/sub2");
        createDirectory("src/new folder/2sub/");
        createFile("src/new folder/2sub/doc40.txt");

        Search.searchDirectory("src/new folder/","src/SearchResults.txt/");
    }

    public static void main(String[] args) {
        new Main().test();
    }
}
