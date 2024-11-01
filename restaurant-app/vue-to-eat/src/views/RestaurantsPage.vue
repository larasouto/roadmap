<script setup lang="ts">
import { computed, ref } from "vue";
import RestaurantCard from "@/components/RestaurantCard.vue";
import { useRestaurantStore } from "@/stores/RestaurantStore";
import SideMenu from "../components/SideMenu.vue";
import type { Restaurant } from "@/types";

const restaurantStore = useRestaurantStore();

const filterText = ref("");

const filteredRestaurants = computed(() => {
	return restaurantStore.list.filter((restaurant: Restaurant) => {
		if (restaurant.name) {
			return restaurant.name
				.toLowerCase()
				.includes(filterText.value.toLowerCase());
		}
		return restaurantStore.list;
	});
});
</script>

<template>
  <main class="section">
    <div class="columns">
      <!-- Side Menu -->
      <SideMenu />
      <div class="column">
        <h1 class="title">Restaurants</h1>
        <!-- CTA Bar -->
        <nav class="level">
          <div class="level-left">
            <div class="level-item">
              <p class="subtitle is-5">
                <strong>{{ restaurantStore.numberOfRestaurants }}</strong> restaurants
              </p>
            </div>

            <p class="level-item">
              <router-link to="/restaurants/new" class="button is-success">New</router-link>
            </p>

            <div class="level-item is-hidden-tablet-only">
              <div class="field has-addons">
                <p class="control">
                  <input class="input" type="text" placeholder="Restaurant name" v-model="filterText" />
                </p>
                <p class="control">
                  <button class="button">Search</button>
                </p>
              </div>
            </div>
          </div>

          <div class="level-right">
            <!-- Optional: Filter Dropdown Component -->
          </div>
        </nav>
        <!-- Display Results -->
        <div class="columns is-multiline">
          <div v-for="item in filteredRestaurants" class="column is-full" :key="`item-${item}`">
            <RestaurantCard :restaurant="item" />
          </div>
        </div>
        <!-- Optional: Pagination Feature -->
      </div>
    </div>
  </main>
</template>