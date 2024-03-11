import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

public class RecipeFilter {

    public static void main(String[] args) {
        try {
            File inputFile = new File("RecipeData.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            List<Element> currentRecipes = getAllRecipes(doc);
            boolean continueFiltering = true;
            Scanner scanner = new Scanner(System.in);

            while (continueFiltering) {
                System.out.println("\n\nSélectionnez l'option de filtrage des recettes :");
                System.out.println("1 - Trier par prix");
                System.out.println("2 - Filtrer par préférence diététique");
                System.out.println("3 - Filtrer par type de cuisine");
                System.out.print("Entrez votre choix (1, 2, 3) : ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Entrez le prix minimum : ");
                        double minPrice = scanner.nextDouble();
                        System.out.print("Entrez le prix maximum : ");
                        double maxPrice = scanner.nextDouble();
                        currentRecipes = filterRecipesByPrice(currentRecipes, minPrice, maxPrice);
                        break;
                    case 2:
                        currentRecipes = filterRecipesByHealth(currentRecipes);
                        break;
                    case 3:
                        currentRecipes = filterRecipesByCuisine(currentRecipes);
                        break;
                    default:
                        System.err.println("Choix non valide. Veuillez réessayer.");
                        break;
                }

                System.out.print("\n\n\nVoulez-vous appliquer un autre filtre ? (Y/N) : ");
                String response = scanner.next();
                continueFiltering = response.equalsIgnoreCase("Y");

                if (!continueFiltering) {
                    System.out.println("\n▶ Recettes après tous les filtres appliqués :");
                    currentRecipes.forEach(RecipeFilter::printRecipe);
                }
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Element> getAllRecipes(Document doc) {
        NodeList nList = doc.getElementsByTagName("recipe");
        List<Element> recipes = new ArrayList<>();
        for (int i = 0; i < nList.getLength(); i++) {
            recipes.add((Element) nList.item(i));
        }
        return recipes;
    }

    private static List<Element> filterRecipesByPrice(List<Element> recipes, double minPrice, double maxPrice) {
        List<Element> filteredRecipes = new ArrayList<>();
        for (Element recipe : recipes) {
            double budget = Double.parseDouble(recipe.getElementsByTagName("budget").item(0).getTextContent());
            if (budget >= minPrice && budget <= maxPrice) {
                filteredRecipes.add(recipe);
                printRecipe(recipe);
            }
        }
        return filteredRecipes;
    }

    private static List<Element> filterRecipesByHealth(List<Element> recipes) {
        Set<String> healthOptions = new HashSet<>();
        for (Element recipe : recipes) {
            String healthValue = recipe.getElementsByTagName("health").item(0).getTextContent();
            healthOptions.add(healthValue);
        }

        if (healthOptions.size() == 1) {
            System.err.println("Il n'y a qu'une option de santé disponible '" + healthOptions.iterator().next() + "'. Aucun filtre ne sera appliqué.");
            return recipes;
        }

        System.out.println("\nOptions de santé disponibles :");
        List<String> sortedHealthOptions = new ArrayList<>(healthOptions);
        Collections.sort(sortedHealthOptions);
        for (int i = 0; i < sortedHealthOptions.size(); i++) {
            System.out.println((i + 1) + " - " + sortedHealthOptions.get(i));
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Sélectionnez le numéro de votre préférence diététique : ");
        int choice = scanner.nextInt();
        String selectedHealthOption = sortedHealthOptions.get(choice - 1);

        List<Element> filteredRecipes = new ArrayList<>();
        for (Element recipe : recipes) {
            String health = recipe.getElementsByTagName("health").item(0).getTextContent();
            if (health.equals(selectedHealthOption)) {
                filteredRecipes.add(recipe);
                printRecipe(recipe);
            }
        }

        return filteredRecipes;
    }

    private static List<Element> filterRecipesByCuisine(List<Element> recipes) {
        Set<String> cuisineOptions = new HashSet<>();
        for (Element recipe : recipes) {
            String cuisineValue = recipe.getElementsByTagName("cuisine").item(0).getTextContent();
            cuisineOptions.add(cuisineValue);
        }

        if (cuisineOptions.size() == 1) {
            System.err.println("Il n'y a qu'une option de cuisine disponible '" + cuisineOptions.iterator().next() + "'. Aucun filtre ne sera appliqué.");
            return recipes;
        }

        System.out.println("\nOptions de cuisine disponibles :");
        List<String> sortedCuisineOptions = new ArrayList<>(cuisineOptions);
        Collections.sort(sortedCuisineOptions);
        for (int i = 0; i < sortedCuisineOptions.size(); i++) {
            System.out.println((i + 1) + " - " + sortedCuisineOptions.get(i));
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Sélectionnez le numéro de votre type de cuisine préféré : ");
        int choice = scanner.nextInt();
        String selectedCuisineOption = sortedCuisineOptions.get(choice - 1);

        List<Element> filteredRecipes = new ArrayList<>();
        for (Element recipe : recipes) {
            String cuisine = recipe.getElementsByTagName("cuisine").item(0).getTextContent();
            if (cuisine.equals(selectedCuisineOption)) {
                filteredRecipes.add(recipe);
                printRecipe(recipe);
            }
        }

        return filteredRecipes;
    }

    private static void printRecipe(Element nNode) {
        System.out.println("\t● Title : " + nNode.getElementsByTagName("title").item(0).getTextContent() +
                " | Description : " + nNode.getElementsByTagName("description").item(0).getTextContent() +
                " | Cuisine : " + nNode.getElementsByTagName("cuisine").item(0).getTextContent() +
                " | Health : " + nNode.getElementsByTagName("health").item(0).getTextContent() +
                " | Budget : " + nNode.getElementsByTagName("budget").item(0).getTextContent());
    }
}
