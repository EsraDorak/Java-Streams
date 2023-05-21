package application;


/**
 * Factory class to create instances of classes, e.g. Numbers.
 * 
 * @author sgra64
 *
 */
public class Factory {

    /**
     * Public factory method to create instances of class Streams
     * or derived classes.
     * 
     * @param numbers argument passed to constructor.
     * @return instance of class Streams or derived class.
     */
    public static PlayStreams getPlayStreamsInstance() {
        PlayStreams instance;
        instance = new PlayStreamsImpl();
//        instance = new PlayStreamsImpl_Complete();
        return instance;
    }
}