package exceptions;

public class PathNotFoundException extends RuntimeException {

   public PathNotFoundException() {

      super("Path annotation is not found on page class");
   }
}
