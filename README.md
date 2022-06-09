# Random Recipes
En mi casa, casi todos los días, aparecía el famoso problema: *¿Que hacemos de comer hoy?* :neutral_face:, seguido por una ola de mal humor provocada por el estrés de no saber que cocinar para una familia completa.

Buscando Apis gratuitas para empezar a desarrollar una app para mi portfolio me encontré con Spoonacular, una api que principalmente está orientada a analizar y buscar información nutricional de comidas, pero que también tiene una base de recetas propia y una función para generar una lista de recetas aleatoria.

Entonces se me ocurrío la idea de crear **Random Recipes**, una app que te permite generar una lista de recetas aleatorias para cuando no sabes que cocinar. Además te dá la opción de guardar las recetas que más te gusten para verlas más tarde, incluso si no tenes conexión a internet, y la posibilidad de buscar recetas filtradas por cocina internacional (como italiana o japonesa) y por dieta (como vegana o libre de gluten).

## Screenshots
![Screenshot_randomrecipes](https://user-images.githubusercontent.com/89859672/171191303-9dd712ec-5706-417c-a717-3a4834628391.jpg)
![Screenshot_recipeslist](https://user-images.githubusercontent.com/89859672/171191374-35b6a8e0-97f0-49ad-abee-e4faeeffc2e3.jpg)
![Screenshot_recipedetails](https://user-images.githubusercontent.com/89859672/171191387-c65e11d5-5fa7-4f9a-992d-0ee36666bce2.jpg)
![Screenshot_recipedetails_2](https://user-images.githubusercontent.com/89859672/171191402-071a08e9-dbb3-41cf-992a-dae38711a843.jpg)
![Screenshot_searchrecipes](https://user-images.githubusercontent.com/89859672/171191410-aafde291-b2f6-43df-ba4f-c17395fdf452.jpg)
![Screenshot_savedrecipes](https://user-images.githubusercontent.com/89859672/171191420-85fcf2d5-5c3a-4e40-8665-565474a1fc20.jpg)

## Arquitectura
- MVVM
- Clean Architecture

## Librerias
- Dagger-Hilt
- Coroutines
- Retrofit
- Room
- Picasso
- Lottie

## Instalación
Clonar el repositorio desde Git Bash con
```
$ git clone https://github.com/Juan-Mozo/Random-Recipes.git
```
O descargar el proyecto en formato zip desde la página principal del repositorio

La Api Key está oculta, por lo que para hacer funcionar la app deberá generar una key desde [Spoonacular](https://spoonacular.com/food-api/console#Profile)
y reemplazar API_KEY al final del archivo por tu key generada en:
```
app/src/main/java/com/juanimozo/recipesrandomizer/data/remote/SpoonacularApi.kt
```
Ejemplo:
```
const val API_KEY = "65dma24d242648ecc66387ecc87dae0a"
```

## To-Do
- Modularización

## Acknowledgements
Bottom navigation icons created by [Freepik](https://www.freepik.com/)
