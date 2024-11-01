import { createRouter, createWebHistory } from "vue-router";
import HomePage from "../views/HomePage.vue";

const router = createRouter({
	history: createWebHistory(import.meta.env.BASE_URL),
	routes: [
		{
			path: "/",
			name: "home",
			component: HomePage,
		},
		{
			path: "/dishes",
			name: "dishes",
			// route level code-splitting
			// this generates a separate chunk (About.[hash].js) for this route
			// which is lazy-loaded when the route is visited.
			component: () => import("../views/DishesPage.vue"),
		},
		{
			path: "/restaurants",
			name: "restaurants",
			component: () => import("../views/RestaurantsPage.vue"),
			// children: [
			//   {
			//     path: 'new',
			//     name: 'create-new-restaurant',
			//     component: () => import('../views/NewRestaurantPage.vue')
			//   }
			// ]
		},
		{
			path: "/restaurants/edit/:id",
			name: "edit-restaurant",
			component: () => import("../views/EditRestaurantPage.vue"),
		},
		{
			path: "/restaurants/new",
			name: "new-restaurant",
			component: () => import("../views/NewRestaurantPage.vue"),
		},
	],
});

export default router;
