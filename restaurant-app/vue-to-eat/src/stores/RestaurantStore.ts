import { useStorage } from "@vueuse/core";
import { defineStore } from "pinia";
import type { Restaurant, StatusResponse } from "../types";

export const useRestaurantStore = defineStore({
	id: "RestaurantStore",
	state: () => ({
		list: useStorage("toast-restaurant-list", [] as Restaurant[]),
	}),
	getters: {
		numberOfRestaurants: (state): number => state.list.length,
		getRestaurantById: (state) => {
			return (id: string | string[]): Restaurant | undefined => {
				if (typeof id === "string") {
					return state.list.find(
						(restaurant: Restaurant) => restaurant.id === id,
					);
				}
			};
		},
	},
	actions: {
		addRestaurant(restaurant: Restaurant): void {
			this.list.push(restaurant);
			window.location.href = "/restaurants";
		},
		deleteRestaurant(restaurant: Restaurant): void {
			this.list.splice(this.list.indexOf(restaurant), 1);
		},
		updateRestaurant(updatedRestaurant: Restaurant): StatusResponse {
			const index = this.list.findIndex(
				(restaurant) => restaurant.id === updatedRestaurant.id,
			);
			const updatedItem = this.list.splice(index, 1, updatedRestaurant);

			if (updatedItem.length > 0) {
				return {
					status: "success",
					message: "Restaurant updated successfully.",
				};
			}

			return {
				status: "error",
				message: "Restaurant unable to be updated.",
			};
		},
	},
});
