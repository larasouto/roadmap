<script setup lang="ts">
import { restaurantStatusList } from "@/constants";
import { useRestaurantStore } from "@/stores/RestaurantStore";
import type { Restaurant, StatusResponse } from "@/types";
import { computed, onMounted, reactive, ref } from "vue";
import { useRoute } from "vue-router";
import SideMenu from "../components/SideMenu.vue";

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
	status: "Quero experimentar",
	image: "",
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

const onFileChange = (event: Event) => {
	const target = event.target as HTMLInputElement;

	if (!target.files?.length) {
		return;
	}

	const file = target.files[0];
	const reader = new FileReader();

	reader.onload = (e) => {
		updatedRestaurant.image = e.target?.result as string;
	};

	reader.readAsDataURL(file);
};
</script>

<template>
  <main class="container mt-4">
    <div class="row">
      <SideMenu />
      <div class="col">
        <nav aria-label="breadcrumb">
          <ol class="breadcrumb">
            <li class="breadcrumb-item">
              <router-link to="/restaurants">Restaurantes</router-link>
            </li>
            <li class="breadcrumb-item active" aria-current="page">Editar Restaurante</li>
          </ol>
        </nav>
        <form @submit.prevent="updateRestaurant">
          <div class="mb-3">
            <label for="image" class="form-label">Imagem</label>
            <input
              type="file"
              id="name"
              class="form-control"
              placeholder="Digite o nome do restaurante"
              required
              @change="onFileChange"
            />
          </div>
          <div class="mb-3">
            <label for="name" class="form-label">Nome</label>
            <input
              type="text"
              class="form-control"
              id="name"
              placeholder="Digite o nome do restaurante"
              required
              v-model="updatedRestaurant.name"
            />
          </div>
          <div class="mb-3">
            <label for="status" class="form-label">Status</label>
            <select class="form-select" id="status" v-model="updatedRestaurant.status">
              <option
                v-for="status in restaurantStatusList"
                :key="status"
                :value="status"
              >
                {{ status }}
              </option>
            </select>
          </div>
          <div class="mb-3">
            <label for="address" class="form-label">Endereço</label>
            <input
              type="text"
              class="form-control"
              id="address"
              placeholder="1234 Sugarcane Lane"
              v-model="updatedRestaurant.address"
            />
          </div>
          <div class="mb-4">
            <label for="website" class="form-label">Website</label>
            <input
              type="text"
              class="form-control"
              id="website"
              placeholder="www.restaurante.com.br"
            />
          </div>
          <div class="d-flex gap-2 mb-3">
            <button type="submit" class="btn btn-success">Atualizar</button>
            <router-link to="/restaurants" class="btn btn-light">Cancelar</router-link>
          </div>
          <div v-if="status.status" class="alert alert-info" role="alert">
            {{ status.message }}
          </div>
        </form>
      </div>
    </div>
  </main>
</template>

<style></style>
