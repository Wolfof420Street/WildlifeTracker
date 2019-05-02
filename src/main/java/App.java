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
        //getting new Animals
        get("/animals", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("animals", Animal.all());
            model.put("template", "templates/animal-success.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //getting new Sightings
        get("/sightings", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("sightings", Sighting.all());
            model.put("template", "templates/sighting-success.vtl");
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
        get("/animals/:id", (request, response) -> {
           HashMap<String, Object> model = new HashMap<String,Object>();
           Animal animal = Animal.find(Integer.parseInt(request.params(":id")));
           model.put("animal", animal);
           model.put("template", "templates/animal.vtl");
           return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
        get("/rangers/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<String,Object>();
            Ranger ranger = Ranger.find(Integer.parseInt(request.params(":id")));
            model.put("ranger", ranger);
            model.put("template", "templates/ranger.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
        get("/rangers/:id/sightings/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<String,Object>();
            Ranger ranger = Ranger.find(Integer.parseInt(request.params(":id")));
            Sighting sighting = Sighting.find(Integer.parseInt(request.params(":id")));
            model.put("ranger", ranger);
            model.put("template", "templates/ranger.vtl");
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
            model.put("template", "templates/rangers.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
        post("/animals", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String name = request.queryParams("name");
            Animal newAnimal = new Animal(name);
            newAnimal.save();
            model.put("template", "templates/animals.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
        post("/sightings", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            int animalId = Integer.parseInt(request.queryParams("animalId"));
            String location = request.queryParams("location");
            String rangerName = request.queryParams("rangerName");
            Sighting newSightings = new Sighting(location, animalId, rangerName);
            newSightings.save();
            model.put("template", "templates/sightings.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

    }
}
