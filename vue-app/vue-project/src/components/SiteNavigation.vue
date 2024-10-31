<template>
  <header class="sticky top-0 bg-weather-primary shadow-lg">
    <nav class="container flex flex-col sm:flex-row items-center gap-4 text-white py-6">
      <RouterLink :to="{ name: 'home' }">
        <div class="flex items-center gap-3 flex-1">
          <i class="fa fa-solid fa-sun text-2xl"></i>
          <p class="text-2xl">My Weather App</p>
        </div>
      </RouterLink>
      <div class="flex gap-3 flex-1 justify-end">
        <i
          class="fa fa-solid fa-circle-info text-xl hover:text-weather-secondary duration-150 cursor-pointer"
          @click="toggleModal"
        ></i>
        <i
          class="fa fa-solid fa-plus text-xl hover:text-weather-secondary duration-150 cursor-pointer"
          @click="addCity"
          v-if="route.query.preview"
        ></i>
      </div>
      <BaseModal :modalActive="modalActive" @close-modal="toggleModal">
        <div class="text-black">
          <h1 class="text-2xl mb-1">Sobre</h1>
          <p class="mb-4">
            O My Weather App permite que você acompanhe o clima atual e futuro das cidades de sua escolha.
          </p>
          <h2 class="text-2xl">Como funciona</h2>
          <ol class="list-decimal list-inside mb-4">
            <li>Procure sua cidade digitando o nome na barra de busca.</li>
            <li>
              Selecione uma cidade nos resultados; isso o levará ao clima atual da sua seleção.
            </li>
            <li>
              Acompanhe a cidade clicando no ícone "+" no canto superior direito. Isso salvará a cidade para visualização posterior na página inicial.
            </li>
          </ol>

          <h2 class="text-2xl">Removendo uma cidade</h2>
          <p>
            Se você não quiser mais acompanhar uma cidade, basta selecionar a cidade na página inicial. Na parte inferior da página, haverá uma opção para excluir a cidade.
          </p>
        </div></BaseModal
      >
    </nav>
  </header>
</template>

<script setup>
import { RouterLink, useRoute, useRouter } from "vue-router";
import { uid } from "uid";
import BaseModal from "./BaseModal.vue";
import { ref } from "vue";

const savedCities = ref([]);
const route = useRoute();
const router = useRouter();
const addCity = () => {
	if (localStorage.getItem("savedCities")) {
		savedCities.value = JSON.parse(localStorage.getItem("savedCities"));
	}

	const locationObject = {
		id: uid(),
		state: route.params.state,
		city: route.params.city,
		coords: {
			lat: route.query.lat,
			lon: route.query.lon,
		},
	};

	savedCities.value.push(locationObject);
	localStorage.setItem("savedCities", JSON.stringify(savedCities.value));

	const query = Object.assign({}, route.query);
	// biome-ignore lint/performance/noDelete: <explanation>
	delete query.preview;
	query.id = locationObject.id;
	router.replace({ query });
};

const modalActive = ref(null);

const toggleModal = () => {
	modalActive.value = !modalActive.value;
};
</script>
