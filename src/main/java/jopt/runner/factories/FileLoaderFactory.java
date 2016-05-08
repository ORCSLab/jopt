package jopt.runner.factories;

import java.nio.file.Path;
import jopt.core.FileLoader;
import jopt.core.Loader;
import jopt.exceptions.FactoryException;
import lombok.NonNull;

/**
 * This class defines a loader factory for {@link FileLoader}s. The files are 
 * effectively read when {@link #create() create()} method is called.
 */
public class FileLoaderFactory implements LoaderFactory {
    
    private Class<? extends FileLoader> clazz;
    private Path[] paths;
    
    /**
     * Sole constructor.
     * 
     * @param   clazz
     *          A {@link FileLoader FileLoader} class to be created by this 
     *          factory
     * @param   paths 
     *          A sequence (or array) of paths of files read by the specified 
     *          {@link FileLoader FileLoader}
     * 
     * @throws  NullPointerException
     *          If {@code clazz} is null
     */
    public FileLoaderFactory(@NonNull Class<? extends FileLoader> clazz, Path... paths) {
        this.clazz = clazz;
        this.paths = paths;
    }

    @Override
    public Loader create() throws FactoryException {
        try {
            
            FileLoader loader = (FileLoader) clazz.newInstance();
            loader.read(paths);
            return loader;
            
        } catch (Throwable e) {
            String msg = String.format("The loader factory was not able to create an instance of \"%s\" class.", clazz.getName());
            throw new FactoryException(msg, e);
        }
    }
    
}
