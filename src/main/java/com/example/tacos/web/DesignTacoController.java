package com.example.tacos.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import com.example.tacos.Ingredient;
import com.example.tacos.Ingredient.Type;
import com.example.tacos.Taco;
import com.example.tacos.TacoOrder;

@Slf4j
@Controller
@RequestMapping("/design")
/*The order we create will need to be carried in the session
  so that it can span multiple requests*/
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    /**
     * It will also be invoked when a request is handled and
     * will construct a list of Ingredient objects to be put into the model.
     *
     * @param model Model the Model object that will be passed to the view. Ultimately, data
     *              that’s placed in Model attributes is copied into the servlet request attributes, where the
     *              view can find them and use them to render a page in the user’s browser
     */
    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            /*Add model attributes - the list of ingredients of each type*/
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    /**
     * Populates the Model with an empty TacoOrder object under a key with name "tacoOrder"
     *
     * @return empty TacoOrder object
     */
    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    /**
     * Populates the Model with an empty Taco object under a key with name "design". Taco object is placed into the
     * model so that the view rendered in response to the GET request for /design will have a
     * non-null object to display.
     *
     * @return empty Taco object
     */
    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    /**
     * Filter ingredients by type.
     *
     * @param ingredients ingredients list
     * @param type        type of ingredients to be filtered
     * @return list of ingredients filtered by type
     */
    private Object filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
