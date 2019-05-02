import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;


public class App {
    public static void main(String[] args) {
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);

        staticFileLocation("/public");
        String layout = "templates/layout.vtl";


        // routing the homepage

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("animals", Animal.all());
            model.put("endangeredAnimals", EndangeredAnimals.all());
            model.put("sighting", Sighting.all());
            model.put("rangers", Ranger.all());
            model.put("template", "templates/index.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());


        // route to add new animal
        get("/animal/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("animals", Animal.all());
            model.put("endangeredAnimals", EndangeredAnimals.all());
            model.put("template", "templates/animal-form.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());


        // route to add new ranger
        get("/rangers/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String name = request.params("name");
            String contactInfo = request.params("contactInfo");
            Ranger ranger = new Ranger(name, contactInfo);
            ranger.save();
            model.put("template", "templates/ranger-form.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //getting new Rangers
        get("/rangers", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("rangers", Ranger.all());
        model.put("template", "templates/rangers.vtl");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
        // getting the endangered animals

        get("/endangeredanimals/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String health = request.params("health");
            String age = request.params("age");
            String name = request.params("name");
            EndangeredAnimals endangeredAnimals = new EndangeredAnimals(name, health, age);
            endangeredAnimals.save();
            model.put("template", "templates/endangered-animals-form.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //checking if animal is normal or endangered
        post("/animal/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String type = request.queryParams("type");

            System.out.println(type);
            if(type.equals("endangered"))
            {
                response.redirect("/endangeredanimals/new");
            }
            else {
                String name = request.queryParams("name");
                Animal Animal = new Animal(name);
                Animal.save();
                model.put("animals", Animal.all());
                model.put("template", "templates/animal-success.vtl");
            }
                return new ModelAndView(model, layout);
            }, new VelocityTemplateEngine());

        post("/endangeredanimals/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String health = request.params("health");
            String age = request.params("age");
            String name = request.params("name");
            EndangeredAnimals endangeredAnimals = new EndangeredAnimals(name, health, age);
            endangeredAnimals.save();
            model.put("endangeredAnimals", EndangeredAnimals.all());
            model.put("template", "templates/endangered-animals-sucess.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
        post("/rangers", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String name = request.queryParams("name");
            String contactInfo = request.queryParams("contactInfo");
            Ranger newRanger = new Ranger(name, contactInfo);
            newRanger.save();
            model.put("template", "templates/stylist-success.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

    }
}
