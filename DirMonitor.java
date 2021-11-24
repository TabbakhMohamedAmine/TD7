import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;

public class DirMonitor{
	
	public DirMonitor(String path) throws Exception_no_path_find {
		FileSystem fs = FileSystems.getDefault();
		Path chemin = fs.getPath(path);
		try {
			chemin.getFileSystem().provider().checkAccess(chemin,AccessMode.READ);
			System.out.println("Path readable !");
		}catch(IOException e) {
			throw new Exception_no_path_find();
		}
	}
	public void affiche_repertoire(String path) throws IOException {
		FileSystem fs = FileSystems.getDefault();
		Path chemin = fs.getPath(path);
		int number_element = chemin.getNameCount();
		System.out.println("Il y'a"+number_element+"elements");
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(chemin)) {
            Iterator<Path> iterator = ds.iterator();
            while(iterator.next()!=null) {
            	 Path p = iterator.next();
                 System.out.println(p);
            }
		}
	}
	public long sizeOfFiles(String path) throws IOException {
		FileSystem fs = FileSystems.getDefault();
		Path chemin = fs.getPath(path);
		long size_finaly = 0;
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(chemin)) {
            Iterator<Path> iterator = ds.iterator();
            while(iterator.next()!=null) {
            	 Path p = iterator.next();
            	 size_finaly+=Files.size(p);
            }
		}
		return size_finaly;
	}
	public File mostRecent(String path) throws IOException{
		File path_fichiers = new File(path);
	    File[] tableau_avec_files = path_fichiers.listFiles();
	    if (tableau_avec_files == null || tableau_avec_files.length == 0) {
	        throw new IOException();
	    }
	    File dernier = tableau_avec_files[0];
	    for (int i = 1; i < tableau_avec_files.length; i++) {
	       if (dernier.lastModified() < tableau_avec_files[i].lastModified()) {
	    	   dernier = tableau_avec_files[i];
	       }
	    }
	    return dernier;
	}
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

}
