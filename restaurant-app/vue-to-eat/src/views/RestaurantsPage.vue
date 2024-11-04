<script setup lang="ts">
import RestaurantCard from "@/components/RestaurantCard.vue";
import { useRestaurantStore } from "@/stores/RestaurantStore";
import type { Restaurant } from "@/types";
import { computed, ref } from "vue";
import SideMenu from "../components/SideMenu.vue";

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
  <main class="container py-5">
    <div class="row">
      <!-- Side Menu -->
      <SideMenu class="col-md-3" />
      <div class="col-md-9">
        <h1 class="display-4 mb-4">Restaurantes</h1>
        
        <!-- CTA Bar -->
        <div class="d-flex justify-content-between align-items-center mb-4">
          <div>
            <p class="fs-5 mb-0">
              <strong>{{ restaurantStore.numberOfRestaurants }}</strong> restaurantes
            </p>
          </div>

          <div class="d-flex gap-2 align-items-center">
            <router-link to="/restaurants/new" class="btn btn-success">Novo</router-link>
            
            <div class="d-none d-md-flex">
              <input 
                type="text" 
                class="form-control" 
                placeholder="Nome do restaurante" 
                v-model="filterText" 
              />
              <button class="btn btn-secondary ms-2">Buscar</button>
            </div>
          </div>
        </div>

        <!-- Display Results -->
        <div class="row row-cols-1 g-4">
          <div v-for="item in filteredRestaurants" class="col" :key="`item-${item.id}`">
            <RestaurantCard :restaurant="item" />
          </div>
        </div>
        
        <!-- Optional: Pagination Feature -->
      </div>
    </div>
  </main>
</template>
