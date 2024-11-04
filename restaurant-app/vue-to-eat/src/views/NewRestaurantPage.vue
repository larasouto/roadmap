<script setup lang="ts">
import { useRestaurantStore } from "@/stores/RestaurantStore";
import type { Restaurant } from "@/types";
import { v4 as uuidv4 } from "uuid";
import { ref } from "vue";
import SideMenu from "../components/SideMenu.vue";

const restaurantStore = useRestaurantStore();

const newRestaurant = ref<Restaurant>({
	id: uuidv4(),
	name: "",
	address: "",
	website: "",
	status: "Quero experimentar",
	image: "",
});

const onFileChange = (event: Event) => {
	const target = event.target as HTMLInputElement;

	if (!target.files?.length) {
		return;
	}

	const file = target.files[0];
	const reader = new FileReader();

	reader.onload = (e) => {
		newRestaurant.value.image = e.target?.result as string;
	};

	reader.readAsDataURL(file);
};
</script>

<template>
  <main class="container py-5">
    <div class="row">
      <SideMenu class="col-md-3" />
      <div class="col-md-9">
        <nav aria-label="breadcrumb">
          <ol class="breadcrumb">
            <li class="breadcrumb-item">
              <router-link to="/restaurants">Restaurants</router-link>
            </li>
            <li class="breadcrumb-item active" aria-current="page">Novo Restaurante</li>
          </ol>
        </nav>
        <form @submit.prevent>
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
              id="name"
              class="form-control"
              placeholder="Digite o nome do restaurante"
              required
              v-model="newRestaurant.name"
            />
          </div>
          <div class="mb-3">
            <label for="address" class="form-label">Endereço</label>
            <input
              type="text"
              id="address"
              class="form-control"
              placeholder="Digite o endereço do restaurante"
              v-model="newRestaurant.address"
            />
          </div>
          <div class="mb-4">
            <label for="website" class="form-label">Website</label>
            <input
              type="text"
              id="website"
              class="form-control"
              placeholder="www.website.com.br"
              v-model="newRestaurant.website"
            />
          </div>
          <div class="d-flex gap-2">
            <button
              class="btn btn-success"
              @click="restaurantStore.addRestaurant(newRestaurant)"
            >
              Criar
            </button>
            <router-link to="/restaurants" class="btn btn-secondary"
              >Cancelar</router-link
            >
          </div>
        </form>
      </div>
    </div>
  </main>
</template>
