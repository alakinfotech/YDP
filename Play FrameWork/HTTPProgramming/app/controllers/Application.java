package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        return ok("hi hello");
    }
    public static Result output(String name){
        return ok("Got request Your name is: " + name );
    }
  
}
