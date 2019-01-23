package validators;


import play.data.validation.Constraints;
import play.libs.F;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RecipeDescriptionValidator extends Constraints.Validator<String> implements ConstraintValidator<DescriptionWithTwoWordsAtLeast,String>{
    @Override
    public void initialize(DescriptionWithTwoWordsAtLeast descriptionWithTwoWordsAtLeast) { }

    @Override
    public boolean isValid(String recipeDescription) {
        return this.isValid(recipeDescription, null);
    }

    @Override
    public boolean isValid(String recipeDescription, ConstraintValidatorContext context) {
        if(recipeDescription != null){
            recipeDescription = recipeDescription.trim();
            return recipeDescription.contains(" ") && recipeDescription.length()<255;
        }
        return false;
    }

    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
        return new F.Tuple<>("The description is not big enough.", new Object[]{""});
    }
}