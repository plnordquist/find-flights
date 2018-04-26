package fflights.patterns;

public abstract class AbstractPhotoReaderFactory {
    abstract PhotoReader getPhotoReader(String filetype);
}