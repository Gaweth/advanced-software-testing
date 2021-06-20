package validator;

public class WordValidator {

    public static boolean isBlank(String word){
        return word ==null || word.trim().isEmpty();
    }


}
