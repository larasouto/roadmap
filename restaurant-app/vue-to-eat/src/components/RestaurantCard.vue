<script setup lang="ts">
import type { PropType } from "vue";
import { computed } from "vue";
import type { Restaurant } from "@/types";
import { useRestaurantStore } from "@/stores/RestaurantStore";

const props = defineProps({
	restaurant: {
		type: Object as PropType<Restaurant>,
		required: true,
	},
});

const restaurantStore = useRestaurantStore();

const statusColor = computed(() => {
	if (props.restaurant.status === "Want to Try") {
		return "is-primary";
	}
	if (props.restaurant.status === "Recommended") {
		return "is-success";
	}
	if (props.restaurant.status === "Do Not Recommend") {
		return "is-danger";
	}
	return "";
});
</script>

<template>
  <article class="box">
    <div class="media">
      <aside class="media-left">
        <img src="https://placehold.jp/150x150.png" alt="" />
      </aside>
      <div class="media-content">
        <p class="title is-4 is-spaced mb-1">
          {{ restaurant.name }}
        </p>
        <p class="subtitle mb-2">
          <span class="tag" :class="statusColor">{{ restaurant.status }}</span>
        </p>
        <div class="content mb-2">
          {{ restaurant.address }}
        </div>
        <div>
          <router-link :to="`/restaurants/edit/${restaurant.id}`" class="button is-small is-info is-light mr-2">
            Edit
          </router-link>
          <button @click="restaurantStore.deleteRestaurant(restaurant)" class="button is-small is-danger is-light">
            Delete
          </button>
        </div>
      </div>
    </div>
  </article>
</template>

<style></style>