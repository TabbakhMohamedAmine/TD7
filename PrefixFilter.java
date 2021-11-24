import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.IOException;

public class PrefixFilter implements DirectoryStream.Filter<Path>  {
	private long  pluspetitsize;
    public PrefixFilter(int  donnepluspetit){
    	pluspetitsize = donnepluspetit;
    }
    public boolean accept(Path path) throws IOException {
        BasicFileAttributes attribut = Files.readAttributes(path,BasicFileAttributes.class);
        long lataille = attribut.size();
        if(lataille > pluspetitsize)return true;
        return false;
    }
}
