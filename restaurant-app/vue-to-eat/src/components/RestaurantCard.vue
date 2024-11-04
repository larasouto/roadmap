<script setup lang="ts">
import { useRestaurantStore } from "@/stores/RestaurantStore";
import type { Restaurant } from "@/types";
import type { PropType } from "vue";
import { computed } from "vue";

const props = defineProps({
	restaurant: {
		type: Object as PropType<Restaurant>,
		required: true,
	},
});

const restaurantStore = useRestaurantStore();

const statusColor = computed(() => {
	const statusColors = {
		"Quero experimentar": "text-bg-warning",
		Recomendo: "text-bg-success",
		"Não recomendo": "text-bg-danger",
	};
	return statusColors[props.restaurant.status] || "";
});
</script>

<template>
  <article class="card mb-4">
    <div class="row g-0">
      <div class="col-auto">
        <img
          :src="
            props.restaurant.image
              ? props.restaurant.image
              : 'https://placehold.jp/150x150.png'
          "
          alt="Restaurant Image"
          class="img-fluid rounded-start object-cover"
          style="width: 150px; height: 150px"
        />
      </div>
      <div class="col">
        <div class="card-body">
          <h5 class="card-title mb-2">{{ restaurant.name }}</h5>
          <p class="card-subtitle mb-2">
            <span class="badge" :class="statusColor">{{ restaurant.status }}</span>
          </p>
          <p class="card-text mb-2">{{ restaurant.address }}</p>
          <div>
            <router-link
              :to="`/restaurants/edit/${restaurant.id}`"
              class="btn btn-info btn-sm me-2"
            >
              Editar
            </router-link>
            <button
              @click="restaurantStore.deleteRestaurant(restaurant)"
              class="btn btn-danger btn-sm"
            >
              Deletar
            </button>
          </div>
        </div>
      </div>
    </div>
  </article>
</template>
