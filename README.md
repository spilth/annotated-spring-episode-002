# Spring Boot Web Application QuickStart

Now that I have the Spring Boot CLI installed, I'd like to create a simple web application.  To keep it super simple, I'm going to create a page that just displays the current time on the web server. Each time I reload the page I should see the date and time update.

## Initializing the Project

Since the Spring Boot CLI creates a command-line application by default, I'll need a way to specify that I want a web application instead.

Let's take a look at what options we have for the `init` command.

    $ spring help init

So the `init` command can take a list of Spring Boot dependencies that you'd like to use in your project.  You can see the available list by passing `—list` to the init command.

    $ spring init —list

Since we want to build a web app we'll pull in the `web` starter. We also want to be able to serve dynamic pages with some kind of templating system. Freemarker seems like a good candidate for that.

    $ spring init hello-web -d=web,freemarker
    $ cd hello-web

Let's see what we get out of the box. We’ll use the `spring-boot:run` Maven goal to start up our app.

    $ mvn spring-boot:run

Then navigate your browser to `http://localhost:8080/`

And we’re getting an error page. Why is that?

## Simple Controller & Template

In order to respond to a request we need to set up a Controller. A controller lets you control what happens when a particular path is requested in your application.

Lets create a Controller named `HomepageController` with a method called `index` that renders a simple template.

First we make the class. Then we add the @Controller annotation to the class which lets Spring know that it should load this class and treat it as a controller.

To respond to requests for the root path we’ll add a method that is mapped to forward slash. We do this by annotating a method with @RequestMapping and a value of just forward slash.

At a minimum this method needs to return the path of a template to render, so we'll set it’s return type as String and return the string of “index”.

    package demo;
    
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.RequestMapping;
    
    import java.util.Date;
    
    @Controller
    public class DemoController {
    
        @RequestMapping("/")
        public String index() {
            return "index";
        }
    }

Next we create a Freemarker template under `src/main/resources/templates` named `index.ftl`.  For now it can just contain some simple markup.

    <html>
        <head>
            <title>Hello, web!</title>
        </head>
        <body>
            <h1>Hello, web!</h1>
        </body>
    </html>

Let’s restart our server and see if things are working now.

    $ mvn clean spring-boot:run

Great, we’re serving up what basically amounts to a static page.

## Dynamic Content

Let’s add the date and time to the page now. Back in the Controller we’ll add a Model to the signature of our method. Spring will now pass in a model that we can add attributes to and those attributes are then available in our template.  Let’s add the current date.

    package demo;
    
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.RequestMapping;
    
    import java.util.Date;
    
    @Controller
    public class DemoController {
    
        @RequestMapping("/")
        public String index(Model model) {
            model.addAttribute("now", new Date());
    
            return "index";
        }
    }

Then we update the template to render the attribute we added.

    <html>
        <head>
            <title>Hello, web!</title>
        </head>
        <body>
            <h1>Hello, web!</h1>
    
            <p>Right now it is ${now}</p>
        </body>
    </html>

Now kill your server with Control-C and then restart it.

Now it’s complaining about not knowing how to render the date.  Let’s fix that by using some Freemarker functions to make it render correctly and nicely.

    <html>
        <head>
            <title>Hello, web!</title>
        </head>
        <body>
            <h1>Hello, web!</h1>
    
            <p>Right now it is ${now?datetime?string.full}</p>
        </body>
    </html>

Now we know the basics of creating a Controller, assign a method to a path and providing data to our templates.

Next: [Web Application Layouts with Freemarker, WebJars & Bootstrap](https://github.com/spilth/annotated-spring-episode-003)
