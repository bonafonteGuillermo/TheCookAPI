package validators;


import play.data.validation.Constraints;
import play.libs.F;

import javax.validation.ConstraintValidator;

public class RecipeDescriptionValidator extends Constraints.Validator<String> implements ConstraintValidator<DescriptionWithTwoWordsAtLeast, String> {
    @Override
    public void initialize(DescriptionWithTwoWordsAtLeast descriptionWithTwoWordsAtLeast) {
    }

    @Override
    public boolean isValid(String recipeDescription) {
        if (recipeDescription != null) {
            recipeDescription = recipeDescription.trim();
            return recipeDescription.contains(" ") && recipeDescription.length() < 255;
        }
        return false;
    }

    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
        return null;
    }
}