package sample;

public class IllegalOrderException  extends IllegalArgumentException {
    IllegalOrderException(){}
    IllegalOrderException(String st){
        super(st);
    }
}
