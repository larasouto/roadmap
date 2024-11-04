<template>
  <div v-for="city in savedCities" :key="city.id">
    <CityCard :city="city" @click="goToCityView(city)" />
  </div>

  <p v-if="savedCities.length === 0">
    No locations added. To start tracking a location, search in
    the field above.
  </p>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import CityCard from "./CityCard.vue";

const savedCities = ref([]);

const getCities = async () => {
	if (localStorage.getItem("savedCities")) {
		savedCities.value = JSON.parse(localStorage.getItem("savedCities"));
	}

	const requests = [];
	for (const city of savedCities.value) {
		requests.push(
			axios.get("https://api.openweathermap.org/data/2.5/weather", {
				params: {
					lat: city.coords.lat,
					lon: city.coords.lon,
					appid: "27cab703f3a825ed0dcf3ccdd87b203f",
					units: "metric",
				},
			}),
		);
	}

	const weatherData = await Promise.all(requests);

	weatherData.forEach((value, index) => {
		savedCities.value[index].weather = value.data;
	});
};

await getCities();

const router = useRouter();
const goToCityView = (city) => {
	router.push({
		name: "cityView",
		params: { state: city.state, city: city.city },
		query: {
			id: city.id,
			lat: city.coords.lat,
			lon: city.coords.lon,
		},
	});
};
</script>


