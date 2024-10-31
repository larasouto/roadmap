<template>
  <main class="container text-white">
    <div class="pt-4 mb-8 relative">
      <input
        type="text"
        v-model="searchQuery"
        @input="getSearchResults"
        placeholder="Search for a city or state"
        class="w-full py-2 px-1 bg-transparent border-b focus:border-weather-secondary focus:outline-none focus:shadow-[0px_1px_0_0_#004E71]"
      />
      <ul
        class="absolute bg-weather-secondary text-white w-full shadow-md py-2 px-1 top-[66px]"
        v-if="mapboxSearchResults"
      >
        <p class="py-2" v-if="searchError">
          Sorry, something went wrong with the search. Please try again.
        </p>
        <p class="py-2" 
				v-if="!serverError && mapboxSearchResults.length === 0">
          No match your query, please try again.
        </p>
        <template v-else>
          <li
            v-for="searchResult in mapboxSearchResults"
            :key="searchResult.id"
            class="py-2 cursor-pointer"
						@click="previewCity(searchResult)"
          >
            {{ searchResult.place_name }}
          </li>
        </template>
      </ul>
    </div>
    <div class="flex flex-col gap-4">
    <Suspense>
      <CityList />
      <template #fallback>
        <p></p>
      </template>
    </Suspense>
    </div>
  </main>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { useRouter, useRoute } from "vue-router";
import CityList from "../components/CityList.vue";

const router = useRouter();

const previewCity = (searchResult) => {
	const [city, state] = searchResult.place_name.split(",");
	router.push({
		name: "cityView",
		params: {
			state: state.replaceAll(" ", ""),
			city: city,
		},
		query: {
			lat: searchResult.geometry.coordinates[1],
			lon: searchResult.geometry.coordinates[0],
			preview: true,
		},
	});
};

const mapboxAPIKey =
	"pk.eyJ1IjoibGFyYXNvdXRvIiwiYSI6ImNtMnVxd3FsajAzeXEycHB4b25sZDJudTQifQ.tevYaa3HalCymNgbcBDOhg";
const searchQuery = ref("");
const queryTimeout = ref(null);
const mapboxSearchResults = ref(null);
const searchError = ref(null);

const getSearchResults = () => {
	clearTimeout(queryTimeout.value);
	queryTimeout.value = setTimeout(async () => {
		if (searchQuery.value !== "") {
			try {
				const result = await axios.get(
					`https://api.mapbox.com/geocoding/v5/mapbox.places/${searchQuery.value}.json?access_token=${mapboxAPIKey}&types=place`,
				);
				mapboxSearchResults.value = result.data.features;
			} catch {
				searchError.value = true;
			}
			return;
		}
		mapboxSearchResults.value = null;
	}, 300);
};
</script>
