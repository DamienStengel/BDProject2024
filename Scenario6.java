import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.*;

public class Scenario6 {

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
                System.out.println("\n\n■ Opération de filtrage des recettes :");
                System.out.println("1 - Trier par prix");
                System.out.println("2 - Filtrer par préférence diététique");
                System.out.println("3 - Filtrer par type de cuisine");
                System.out.println("4 - Supprimer tous les filtres");
                System.out.println("■ Autre opération : :");
                System.out.println("5 - Afficher toutes les recettes");
                System.out.println("6 - Ajouter une recette");
                System.out.println("7 - Supprimer une recette");
                System.out.println("8 - Quitter le programme");
                System.out.print("Entrez votre choix (1, 2, 3, 4, 5, 6, 7, 8) : ");
                try {

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
                        case 4:
                            currentRecipes = getAllRecipes(doc);
                            System.out.println("✅ Tous les filtres ont été supprimés");
                            break;
                        case 5:
                            System.out.println("\n▶ Toutes les recettes :");
                            currentRecipes.forEach(Scenario6::printRecipe);
                            break;
                        case 6:
                            currentRecipes = addRecipe(doc);
                            break;
                        case 7:
                            currentRecipes = deleteRecipe(doc);
                            break;
                        default:
                            System.err.println("Choix non valide. Veuillez réessayer.");
                            break;
                    }

                    System.out.print("\n\n\nVoulez-vous continuer à effectuer des opérations (Y/N) ? ");
                    String response = scanner.next();
                    continueFiltering = response.equalsIgnoreCase("Y");

                    if (!continueFiltering) {
                        System.out.println("\n▶ Recettes après tous les filtres appliqués :");
                        currentRecipes.forEach(Scenario6::printRecipe);
                    }
                } catch (Exception e) {
                    System.err.println("Choix non valide. Veuillez réessayer.");
                    scanner.next();
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

    private static List<Element> addRecipe(Document doc) {
        try {
            Scanner scanner = new Scanner(System.in);

            Element newRecipe = doc.createElement("recipe");
            newRecipe.setAttribute("id", "r" + (doc.getElementsByTagName("recipe").getLength() + 1));

            System.out.print("Entrez le titre de la recette : ");
            String title = scanner.nextLine();
            Element titleElement = doc.createElement("title");
            titleElement.appendChild(doc.createTextNode(title));
            newRecipe.appendChild(titleElement);

            System.out.print("Entrez la description de la recette : ");
            String description = scanner.nextLine();
            Element descElement = doc.createElement("description");
            descElement.appendChild(doc.createTextNode(description));
            newRecipe.appendChild(descElement);

            System.out.print("Entrez le type de cuisine de la recette : ");
            String cuisine = scanner.nextLine();
            Element cuisineElement = doc.createElement("cuisine");
            cuisineElement.appendChild(doc.createTextNode(cuisine));
            newRecipe.appendChild(cuisineElement);

            System.out.print("Entrez la préférence diététique de la recette : ");
            String health = scanner.nextLine();
            Element healthElement = doc.createElement("health");
            healthElement.appendChild(doc.createTextNode(health));
            newRecipe.appendChild(healthElement);

            System.out.print("Entrez le budget de la recette : ");
            String budget = scanner.nextLine();
            Element budgetElement = doc.createElement("budget");
            budgetElement.appendChild(doc.createTextNode(budget));
            newRecipe.appendChild(budgetElement);

            Element ingredientsElement = doc.createElement("ingredients");
            System.out.println("Saisissez les ingrédients de la recette (Entrez 'fin' pour terminer) :");
            while (true) {
                System.out.print("Nom de l'ingrédient : ");
                String ingredientName = scanner.nextLine();
                if (ingredientName.equals("fin")) break;

                System.out.print("Quantité : ");
                String quantity = scanner.nextLine();

                System.out.print("Unité : ");
                String unit = scanner.nextLine();

                Element ingredientElement = doc.createElement("ingredient");
                ingredientElement.setAttribute("id", "i" + (ingredientsElement.getChildNodes().getLength() + 1));
                Element nameElement = doc.createElement("name");
                nameElement.appendChild(doc.createTextNode(ingredientName));
                Element quantityElement = doc.createElement("quantity");
                quantityElement.appendChild(doc.createTextNode(quantity));
                Element unitElement = doc.createElement("unit");
                unitElement.appendChild(doc.createTextNode(unit));

                ingredientElement.appendChild(nameElement);
                ingredientElement.appendChild(quantityElement);
                ingredientElement.appendChild(unitElement);
                ingredientsElement.appendChild(ingredientElement);
            }
            newRecipe.appendChild(ingredientsElement);

            System.out.print("Entrez les étapes de la recette : ");
            String steps = scanner.nextLine();
            Element stepsElement = doc.createElement("steps");
            stepsElement.appendChild(doc.createTextNode(steps));
            newRecipe.appendChild(stepsElement);

            Element recipesElement = (Element) doc.getElementsByTagName("recipes").item(0);
            recipesElement.appendChild(newRecipe);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("RecipeData.xml"));
            transformer.transform(source, result);

            System.out.println("Nouvelle recette ajoutée avec succès.");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return getAllRecipes(doc);
    }

    private static List<Element> deleteRecipe(Document doc) {
        try {
            NodeList recipeNodes = doc.getElementsByTagName("recipe");
            Scanner scanner = new Scanner(System.in);

            System.out.println("Liste des recettes à supprimer :");
            for (int i = 0; i < recipeNodes.getLength(); i++) {
                Element recipeElement = (Element) recipeNodes.item(i);
                System.out.println((i + 1) + " - " + recipeElement.getElementsByTagName("title").item(0).getTextContent());
            }

            System.out.print("Entrez le numéro de la recette à supprimer (1-" + recipeNodes.getLength() + ") : ");
            int choice = scanner.nextInt();

            if (choice < 1 || choice > recipeNodes.getLength()) {
                System.out.println("Numéro invalide.");
                return getAllRecipes(doc);
            }

            Element recipesElement = (Element) doc.getElementsByTagName("recipes").item(0);
            Node recipeToRemove = recipeNodes.item(choice - 1);
            recipesElement.removeChild(recipeToRemove);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("RecipeData.xml"));
            transformer.transform(source, result);

            System.out.println("Recette supprimée avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getAllRecipes(doc);
    }
}
