<script setup lang="ts">
import { computed, onMounted, reactive, ref } from "vue";
import { useRoute } from "vue-router";
import type { Restaurant, StatusResponse } from "@/types";
import SideMenu from "../components/SideMenu.vue";
import { useRestaurantStore } from "@/stores/RestaurantStore";
import { restaurantStatusList } from "@/constants";

const restaurantStore = useRestaurantStore();

const route = useRoute();

const status = ref<StatusResponse>({
	status: "",
	message: "",
});

const currentRestaurant = computed(() => {
	return restaurantStore.getRestaurantById(route.params.id);
});

const updatedRestaurant = reactive<Restaurant>({
	id: "",
	name: "",
	address: "",
	website: "",
	status: "Want to Try",
});

const updateRestaurant = () => {
	status.value = restaurantStore.updateRestaurant(updatedRestaurant);
};

onMounted(() => {
	if (currentRestaurant.value) {
		updatedRestaurant.id = currentRestaurant.value.id;
		updatedRestaurant.name = currentRestaurant.value.name;
		updatedRestaurant.address = currentRestaurant.value.address;
		updatedRestaurant.website = currentRestaurant.value.website;
		updatedRestaurant.status = currentRestaurant.value.status;
	}
});
</script>

<template>
  <main class="section">
    <div class="columns">
      <SideMenu />
      <div class="column">
        <nav class="breadcrumb">
          <ul>
            <li><router-link to="/restaurants">Restaurants</router-link></li>
            <li class="is-active"><a href="#">Edit Restaurant</a></li>
          </ul>
        </nav>
        <form @submit.prevent>
          <div class="field">
            <div class="field">
              <label for="name" class="label">Name</label>
              <div class="control">
                <input
                  type="text"
                  class="input is-large"
                  placeholder="Sugarcane Lane"
                  required
                  v-model="updatedRestaurant.name"
                />
              </div>
            </div>
            <div class="field">
              <label for="status" class="label">Status</label>
              <div class="control">
                <div class="select">
                  <select v-model="updatedRestaurant.status">
                    <option v-for="status in restaurantStatusList" :value="status">{{ status }}</option>
                  </select>
                </div>
              </div>
            </div>
            <div class="field">
              <label for="address" class="label">Address</label>
              <div class="control">
                <input
                  type="text"
                  class="input"
                  placeholder="1234 Sugarcane Lane"
                  v-model="updatedRestaurant.address"
                />
              </div>
            </div>
            <div class="field mb-5">
              <label for="website" class="label">Website</label>
              <div class="control">
                <input
                  type="text"
                  class="input"
                  placeholder="www.sugarcane-lane.com"
                  v-model="updatedRestaurant.website"
                />
              </div>
            </div>
            <div class="field">
              <div class="buttons">
                <button @click="updateRestaurant" class="button is-success">Update</button>
                <router-link to="/restaurants" class="button is-light">Cancel</router-link>
              </div>
            </div>
            <div v-if="status.status" class="notification is-info">
              {{ status.message }}
            </div>
          </div>
        </form>
      </div>
    </div>
  </main>
</template>

<style></style>