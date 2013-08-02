package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        return internalServerError();
    }
    public static Result output(String name){
        return redirect(routes.Application.index());
    }
  
}
