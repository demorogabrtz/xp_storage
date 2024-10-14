package com.github.charlyb01.xpstorage.recipe;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class XpBookUpgradeRecipeBuilder {
    private final RecipeCategory category;
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;
    private final int baseLevel;
    private final int resultCapacity;
    private final int resultXpFromUsing;
    private final int resultXpFromBrewing;
    private final int resultBarColor;
    private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap<>();

    public XpBookUpgradeRecipeBuilder(RecipeCategory category, Ingredient template, Ingredient base, Ingredient addition,
                                      int baseLevel, int resultCapacity, int resultXpFromUsing, int resultXpFromBrewing,
                                      int resultBarColor) {
        this.category = category;
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.baseLevel = baseLevel;
        this.resultCapacity = resultCapacity;
        this.resultXpFromUsing = resultXpFromUsing;
        this.resultXpFromBrewing = resultXpFromBrewing;
        this.resultBarColor = resultBarColor;
    }

    public static XpBookUpgradeRecipeBuilder create(RecipeCategory category, Ingredient template, Ingredient base,
                                                    Ingredient addition, int baseLevel, int resultCapacity,
                                                    int resultXpFromUsing, int resultXpFromBrewing, int resultBarColor) {
        return new XpBookUpgradeRecipeBuilder(category, template, base, addition, baseLevel,
                resultCapacity, resultXpFromUsing, resultXpFromBrewing, resultBarColor);
    }

    public XpBookUpgradeRecipeBuilder criterion(String name, AdvancementCriterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    public void offerTo(RecipeExporter exporter, Identifier recipeId) {
        this.validate(recipeId);
        Advancement.Builder builder = exporter.getAdvancementBuilder()
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        this.criteria.forEach(builder::criterion);
        XpBookUpgradeRecipe upgradeRecipe = new XpBookUpgradeRecipe(this.template, this.base, this.addition,
                this.baseLevel, this.resultCapacity, this.resultXpFromUsing, this.resultXpFromBrewing, this.resultBarColor);
        exporter.accept(recipeId, upgradeRecipe, builder.build(recipeId.withPrefixedPath("recipes/" + this.category.getName() + "/")));
    }

    private void validate(Identifier recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        }
    }
}