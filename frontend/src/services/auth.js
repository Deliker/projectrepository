import apiService from './api';
import { reactive } from 'vue';

const state = reactive({
  // Current authentication state
    isAuthenticated: false,
    user: null,
    userId: null,
    token: null,
    role: null,
    loading: false,
    error: null
  });

  export default {
    state,
  // Initialize auth state from localStorage
  init() {
    const token = localStorage.getItem('auth_token');
    const user = localStorage.getItem('user');
    const userId = localStorage.getItem('user_id');
    const role = localStorage.getItem('user_role');

    if (token && user && userId) {
      try {
        this.state.token = token;
        this.state.user = JSON.parse(user);
        this.state.userId = parseInt(userId);
        this.state.role = role;
        this.state.isAuthenticated = true;
      } catch (e) {
        console.error('Error parsing stored user data', e);
        this.logout();
      }
    }
  },

  // Register a new user
  async register(name, email, password) {
    this.state.loading = true;
    this.state.error = null;
    
    try {
      await apiService.register({ name, email, password });
      
      // Automatically log in after successful registration
      return this.login(email, password);
    } catch (error) {
      this.state.error = error.response?.data?.error || 'Registration failed';
      this.state.loading = false;
      throw error;
    }
  },

  // Login user
  async login(email, password) {
    this.state.loading = true;
    this.state.error = null;
    
    try {
      const response = await apiService.login({ email, password });
      
      const { id, name, email: userEmail, token, role } = response.data;
      
      // Store auth data
      this.state.isAuthenticated = true;
      this.state.user = { name, email: userEmail };
      this.state.userId = id;
      this.state.token = token;
      this.state.role = role || 'USER'; 
      
      // Persist to localStorage
      localStorage.setItem('auth_token', token);
      localStorage.setItem('user', JSON.stringify(this.state.user));
      localStorage.setItem('user_id', id);
      localStorage.setItem('user_role', this.state.role);
      
      this.state.loading = false;
      return response.data;
    } catch (error) {
      this.state.error = error.response?.data?.error || 'Login failed';
      this.state.loading = false;
      throw error;
    }
  },

  // Logout user
  logout() {
    // Clear state
    this.state.isAuthenticated = false;
    this.state.user = null;
    this.state.userId = null;
    this.state.token = null;
    this.state.role = null;
    
    // Clear localStorage
    localStorage.removeItem('auth_token');
    localStorage.removeItem('user');
    localStorage.removeItem('user_id');
    localStorage.removeItem('user_role');
  },

  // Get user profile data
  async getUserProfile() {
    if (!this.state.isAuthenticated || !this.state.userId) {
      throw new Error('User not authenticated');
    }
    
    try {
      const response = await apiService.getUserInfo(this.state.userId);
      return response.data;
    } catch (error) {
      console.error('Error fetching user profile:', error);
      throw error;
    }
  },

  // Check if user is authenticated
  isAuthenticated() {
    return this.state.isAuthenticated;
  },

  // Check if user has admin role
  isAdmin() {
    return this.state.role === 'ADMIN';
  },

  // Get current user
  getCurrentUser() {
    return this.state.user;
  },

  // Get user ID
  getUserId() {
    return this.state.userId;
  },
  
  // Get user role
  getUserRole() {
    return this.state.role;
  }
};