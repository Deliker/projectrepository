<template>
  <div class="admin-page">
    <div v-if="!isAdmin" class="unauthorized-message">
      <div class="unauthorized-icon">⚠️</div>
      <h2>{{ $t('admin.unauthorized.title') }}</h2>
      <p>{{ $t('admin.unauthorized.message') }}</p>
      <router-link to="/" class="btn btn-primary">{{ $t('admin.unauthorized.backHome') }}</router-link>
    </div>

    <div v-else class="admin-container">
      <div class="admin-header">
        <h1 class="admin-title">{{ $t('admin.title') }}</h1>
        <p class="admin-subtitle">{{ $t('admin.subtitle') }}</p>
      </div>
      
      <div class="admin-stats">
        <div class="stat-card">
          <div class="stat-icon-container">
            <div class="stat-icon">👥</div>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.totalUsers }}</div>
            <div class="stat-label">{{ $t('admin.stats.totalUsers') }}</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon-container">
            <div class="stat-icon">📝</div>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.totalTasks }}</div>
            <div class="stat-label">{{ $t('admin.stats.totalTasks') }}</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon-container">
            <div class="stat-icon">🏆</div>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.totalAchievements }}</div>
            <div class="stat-label">{{ $t('admin.stats.totalAchievements') }}</div>
          </div>
        </div>
      </div>
      
      <div class="admin-tabs-container">
        <div class="admin-tabs">
          <button 
            class="tab-button" 
            :class="{ active: activeTab === 'users' }" 
            @click="activeTab = 'users'"
          >
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <circle cx="9" cy="7" r="4" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <path d="M23 21v-2a4 4 0 0 0-3-3.87" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <path d="M16 3.13a4 4 0 0 1 0 7.75" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            {{ $t('admin.tabs.users') }}
          </button>
          <button 
            class="tab-button" 
            :class="{ active: activeTab === 'tasks' }" 
            @click="activeTab = 'tasks'"
          >
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M9 11L12 14L22 4" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <path d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            {{ $t('admin.tabs.tasks') }}
          </button>
          <button 
            class="tab-button" 
            :class="{ active: activeTab === 'achievements' }" 
            @click="activeTab = 'achievements'"
          >
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M12 15C8.13401 15 5 11.866 5 8V3H19V8C19 11.866 15.866 15 12 15Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <path d="M8.21 13.89L7 23L12 20L17 23L15.79 13.88" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            {{ $t('admin.tabs.achievements') }}
          </button>
        </div>
      </div>
      
      <!-- Users Tab Content -->
      <div v-if="activeTab === 'users'" class="tab-content">
        <div class="content-header">
          <h2>{{ $t('admin.users.title') }}</h2>
          <button class="btn btn-primary" @click="showAddUserModal = true">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M12 5v14M5 12h14" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            <span>{{ $t('admin.users.addUser') }}</span>
          </button>
        </div>
        
        <div class="search-filter">
          <div class="search-box">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" class="search-icon">
              <circle cx="11" cy="11" r="8" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <path d="M21 21l-4.35-4.35" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            <input 
              type="text" 
              v-model="userSearchQuery" 
              :placeholder="$t('admin.search')" 
              class="search-input"
            />
          </div>
        </div>
        
        <div class="table-container">
          <table class="data-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>{{ $t('admin.users.name') }}</th>
                <th>{{ $t('admin.users.email') }}</th>
                <th>{{ $t('admin.users.role') }}</th>
                <th>{{ $t('admin.actions') }}</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="user in filteredUsers" :key="user.id">
                <td>{{ user.id }}</td>
                <td>{{ user.name }}</td>
                <td>{{ user.email }}</td>
                <td>
                  <span class="role-badge" :class="user.role.toLowerCase()">
                    {{ user.role }}
                  </span>
                </td>
                <td class="actions-cell">
                  <button class="action-btn edit" @click="editUser(user)">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                      <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                    <span class="tooltip">Edit</span>
                  </button>
                  <button class="action-btn delete" @click="confirmDeleteUser(user)">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M3 6h18M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2M10 11v6M14 11v6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                    <span class="tooltip">Delete</span>
                  </button>
                </td>
              </tr>
              <tr v-if="filteredUsers.length === 0">
                <td colspan="5" class="empty-message">No users found matching your search criteria.</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      
      <!-- Tasks Tab Content -->
      <div v-if="activeTab === 'tasks'" class="tab-content">
        <div class="content-header">
          <h2>{{ $t('admin.tasks.title') }}</h2>
        </div>
        
        <div class="search-filter">
          <div class="search-box">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" class="search-icon">
              <circle cx="11" cy="11" r="8" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <path d="M21 21l-4.35-4.35" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            <input 
              type="text" 
              v-model="taskSearchQuery" 
              :placeholder="$t('admin.search')" 
              class="search-input"
            />
          </div>
        </div>
        
        <div class="table-container">
          <table class="data-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>{{ $t('admin.tasks.description') }}</th>
                <th>{{ $t('admin.tasks.category') }}</th>
                <th>{{ $t('admin.tasks.priority') }}</th>
                <th>{{ $t('admin.tasks.date') }}</th>
                <th>{{ $t('admin.tasks.status') }}</th>
                <th>{{ $t('admin.tasks.user') }}</th>
                <th>{{ $t('admin.actions') }}</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="task in filteredTasks" :key="task.id">
                <td>{{ task.id }}</td>
                <td>{{ task.description }}</td>
                <td>
                  <span class="category-badge" :style="{ backgroundColor: getCategoryColor(task.category) + '20', color: getCategoryColor(task.category) }">
                    {{ task.category }}
                  </span>
                </td>
                <td>
                  <span class="priority-badge" :class="task.priority">
                    {{ task.priority }}
                  </span>
                </td>
                <td>{{ formatDate(task.year, task.month, task.day) }}</td>
                <td>
                  <span class="status-badge" :class="task.completed ? 'completed' : 'pending'">
                    {{ task.completed ? $t('admin.tasks.completed') : $t('admin.tasks.pending') }}
                  </span>
                </td>
                <td>{{ getUserName(task.user.id) }}</td>
                <td class="actions-cell">
                  <button class="action-btn delete" @click="confirmDeleteTask(task)">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M3 6h18M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2M10 11v6M14 11v6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                    <span class="tooltip">Delete</span>
                  </button>
                </td>
              </tr>
              <tr v-if="filteredTasks.length === 0">
                <td colspan="8" class="empty-message">No tasks found matching your search criteria.</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      
      <!-- Achievements Tab Content -->
      <div v-if="activeTab === 'achievements'" class="tab-content">
        <div class="content-header">
          <h2>{{ $t('admin.achievements.title') }}</h2>
        </div>
        
        <div class="search-filter">
          <div class="search-box">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" class="search-icon">
              <circle cx="11" cy="11" r="8" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <path d="M21 21l-4.35-4.35" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            <input 
              type="text" 
              v-model="achievementSearchQuery" 
              :placeholder="$t('admin.search')" 
              class="search-input"
            />
          </div>
        </div>
        
        <div class="table-container">
          <table class="data-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>{{ $t('admin.achievements.title') }}</th>
                <th>{{ $t('admin.achievements.description') }}</th>
                <th>{{ $t('admin.achievements.category') }}</th>
                <th>{{ $t('admin.achievements.points') }}</th>
                <th>{{ $t('admin.achievements.user') }}</th>
                <th>{{ $t('admin.achievements.unlockDate') }}</th>
                <th>{{ $t('admin.actions') }}</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="achievement in filteredAchievements" :key="achievement.id">
                <td>{{ achievement.id }}</td>
                <td class="achievement-title-cell">
                  <span class="achievement-icon">{{ achievement.icon }}</span>
                  {{ achievement.title }}
                </td>
                <td>{{ achievement.description }}</td>
                <td>
                  <span class="category-badge">{{ achievement.category }}</span>
                </td>
                <td class="points-cell">
                  <span class="points-badge">+{{ achievement.points }}</span>
                </td>
                <td>{{ getUserName(achievement.user.id) }}</td>
                <td>{{ formatDateTime(achievement.unlockedAt) }}</td>
                <td class="actions-cell">
                  <button class="action-btn delete" @click="confirmDeleteAchievement(achievement)">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M3 6h18M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2M10 11v6M14 11v6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                    <span class="tooltip">Delete</span>
                  </button>
                </td>
              </tr>
              <tr v-if="filteredAchievements.length === 0">
                <td colspan="8" class="empty-message">No achievements found matching your search criteria.</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    
    <!-- Add User Modal -->
    <div v-if="showAddUserModal" class="modal">
      <div class="modal-backdrop" @click="showAddUserModal = false"></div>
      <div class="modal-content">
        <div class="modal-header">
          <h3>{{ $t('admin.users.addUser') }}</h3>
          <button class="close-btn" @click="showAddUserModal = false">×</button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label for="name">{{ $t('admin.users.name') }}</label>
            <input type="text" id="name" v-model="newUser.name" required>
          </div>
          <div class="form-group">
            <label for="email">{{ $t('admin.users.email') }}</label>
            <input type="email" id="email" v-model="newUser.email" required>
          </div>
          <div class="form-group">
            <label for="password">{{ $t('admin.users.password') }}</label>
            <input type="password" id="password" v-model="newUser.password" required>
          </div>
          <div class="form-group">
            <label for="role">{{ $t('admin.users.role') }}</label>
            <select id="role" v-model="newUser.role">
              <option value="USER">USER</option>
              <option value="ADMIN">ADMIN</option>
            </select>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-cancel" @click="showAddUserModal = false">{{ $t('admin.cancel') }}</button>
          <button class="btn btn-primary" @click="addUser">{{ $t('admin.save') }}</button>
        </div>
      </div>
    </div>
    
    <!-- Edit User Modal -->
    <div v-if="showEditUserModal" class="modal">
      <div class="modal-backdrop" @click="showEditUserModal = false"></div>
      <div class="modal-content">
        <div class="modal-header">
          <h3>{{ $t('admin.users.editUser') }}</h3>
          <button class="close-btn" @click="showEditUserModal = false">×</button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label for="edit-name">{{ $t('admin.users.name') }}</label>
            <input type="text" id="edit-name" v-model="editingUser.name" required>
          </div>
          <div class="form-group">
            <label for="edit-email">{{ $t('admin.users.email') }}</label>
            <input type="email" id="edit-email" v-model="editingUser.email" required>
          </div>
          <div class="form-group">
            <label for="edit-password">{{ $t('admin.users.newPassword') }}</label>
            <input type="password" id="edit-password" v-model="editingUser.password" placeholder="Leave blank to keep current password">
          </div>
          <div class="form-group">
            <label for="edit-role">{{ $t('admin.users.role') }}</label>
            <select id="edit-role" v-model="editingUser.role">
              <option value="USER">USER</option>
              <option value="ADMIN">ADMIN</option>
            </select>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-cancel" @click="showEditUserModal = false">{{ $t('admin.cancel') }}</button>
          <button class="btn btn-primary" @click="updateUser">{{ $t('admin.save') }}</button>
        </div>
      </div>
    </div>
    
    <!-- Confirmation Modal -->
    <div v-if="showConfirmModal" class="modal">
      <div class="modal-backdrop" @click="showConfirmModal = false"></div>
      <div class="modal-content">
        <div class="modal-header">
          <h3>{{ confirmModalTitle }}</h3>
          <button class="close-btn" @click="showConfirmModal = false">×</button>
        </div>
        <div class="modal-body">
          <div class="confirm-icon-container">
            <div class="confirm-icon">⚠️</div>
          </div>
          <p class="confirm-message">{{ confirmModalMessage }}</p>
        </div>
        <div class="modal-footer">
          <button class="btn btn-cancel" @click="showConfirmModal = false">{{ $t('admin.cancel') }}</button>
          <button class="btn btn-danger" @click="confirmAction">{{ $t('admin.delete') }}</button>
        </div>
      </div>
    </div>
  </div>
</template>
  
  <script>
  import { ref, computed, onMounted } from 'vue';
import apiService from '../services/api';

export default {
  setup() {
    // State
    const isAdmin = computed(() => {
      return localStorage.getItem('user_role') === 'ADMIN';
    });
    
    const activeTab = ref('users');
    const stats = ref({
      totalUsers: 0,
      totalTasks: 0,
      totalAchievements: 0
    });
    
    // Users
    const users = ref([]);
    const userSearchQuery = ref('');
    const showAddUserModal = ref(false);
    const showEditUserModal = ref(false);
    const newUser = ref({
      name: '',
      email: '',
      password: '',
      role: 'USER'
    });
    const editingUser = ref({
      id: null,
      name: '',
      email: '',
      password: '',
      role: 'USER'
    });
    
    const filteredUsers = computed(() => {
      if (!userSearchQuery.value) return users.value;
      const query = userSearchQuery.value.toLowerCase();
      return users.value.filter(user => 
        user.name.toLowerCase().includes(query) || 
        user.email.toLowerCase().includes(query) ||
        user.role.toLowerCase().includes(query)
      );
    });
    
    // Tasks
    const tasks = ref([]);
    const taskSearchQuery = ref('');
    
    const filteredTasks = computed(() => {
      if (!taskSearchQuery.value) return tasks.value;
      const query = taskSearchQuery.value.toLowerCase();
      return tasks.value.filter(task => 
        task.description.toLowerCase().includes(query) || 
        task.category.toLowerCase().includes(query) ||
        task.priority.toLowerCase().includes(query)
      );
    });
    
    // Achievements
    const achievements = ref([]);
    const achievementSearchQuery = ref('');
    
    const filteredAchievements = computed(() => {
      if (!achievementSearchQuery.value) return achievements.value;
      const query = achievementSearchQuery.value.toLowerCase();
      return achievements.value.filter(achievement => 
        achievement.title.toLowerCase().includes(query) || 
        achievement.description.toLowerCase().includes(query) ||
        achievement.category.toLowerCase().includes(query)
      );
    });
    
    // Category colors
    const categoryColors = {
      work: '#4CAF50',
      personal: '#2196F3',
      study: '#9C27B0',
      health: '#F44336',
      shopping: '#FF9800',
      other: '#757575'
    };
    
    // Confirmation Modal
    const showConfirmModal = ref(false);
    const confirmModalTitle = ref('');
    const confirmModalMessage = ref('');
    const confirmAction = ref(() => {});
    
    // Methods
    const loadStats = async () => {
      try {
        const adminId = localStorage.getItem('user_id');
        const response = await apiService.get(`/admin/stats?adminId=${adminId}`);
        stats.value = response.data;
      } catch (error) {
        console.error('Error loading stats:', error);
      }
    };
    
    const loadUsers = async () => {
      try {
        const adminId = localStorage.getItem('user_id');
        const response = await apiService.get(`/admin/users?adminId=${adminId}`);
        users.value = response.data;
      } catch (error) {
        console.error('Error loading users:', error);
      }
    };
    
    const loadTasks = async () => {
      try {
        const adminId = localStorage.getItem('user_id');
        const response = await apiService.get(`/admin/tasks?adminId=${adminId}`);
        tasks.value = response.data;
      } catch (error) {
        console.error('Error loading tasks:', error);
      }
    };
    
    const loadAchievements = async () => {
      try {
        const adminId = localStorage.getItem('user_id');
        const response = await apiService.get(`/admin/achievements?adminId=${adminId}`);
        achievements.value = response.data;
      } catch (error) {
        console.error('Error loading achievements:', error);
      }
    };
    
    const addUser = async () => {
      try {
        const adminId = localStorage.getItem('user_id');
        const response = await apiService.post(`/admin/users?adminId=${adminId}`, newUser.value);
        users.value.push(response.data);
        showAddUserModal.value = false;
        
        // Reset form
        newUser.value = {
          name: '',
          email: '',
          password: '',
          role: 'USER'
        };
        
        // Refresh stats
        loadStats();
      } catch (error) {
        console.error('Error adding user:', error);
        alert('Error adding user: ' + (error.response?.data?.error || error.message));
      }
    };
    
    const editUser = (user) => {
      editingUser.value = {
        id: user.id,
        name: user.name,
        email: user.email,
        password: '',
        role: user.role
      };
      showEditUserModal.value = true;
    };
    
    const updateUser = async () => {
      try {
        const adminId = localStorage.getItem('user_id');
        const response = await apiService.put(`/admin/users/${editingUser.value.id}?adminId=${adminId}`, editingUser.value);
        
        // Update user in list
        const index = users.value.findIndex(u => u.id === editingUser.value.id);
        if (index !== -1) {
          users.value[index] = response.data;
        }
        
        showEditUserModal.value = false;
      } catch (error) {
        console.error('Error updating user:', error);
        alert('Error updating user: ' + (error.response?.data?.error || error.message));
      }
    };
    
    const confirmDeleteUser = (user) => {
      confirmModalTitle.value = 'Delete User';
      confirmModalMessage.value = `Are you sure you want to delete the user "${user.name}"? This action cannot be undone.`;
      confirmAction.value = () => deleteUser(user.id);
      showConfirmModal.value = true;
    };
    
    const deleteUser = async (userId) => {
      try {
        const adminId = localStorage.getItem('user_id');
        await apiService.delete(`/admin/users/${userId}?adminId=${adminId}`);
        
        // Remove user from list
        users.value = users.value.filter(u => u.id !== userId);
        
        // Refresh stats
        loadStats();
        
        showConfirmModal.value = false;
      } catch (error) {
        console.error('Error deleting user:', error);
        alert('Error deleting user: ' + (error.response?.data?.error || error.message));
      }
    };
    
    const confirmDeleteTask = (task) => {
      confirmModalTitle.value = 'Delete Task';
      confirmModalMessage.value = `Are you sure you want to delete the task "${task.description}"? This action cannot be undone.`;
      confirmAction.value = () => deleteTask(task.id);
      showConfirmModal.value = true;
    };
    
    const deleteTask = async (taskId) => {
      try {
        const adminId = localStorage.getItem('user_id');
        await apiService.delete(`/admin/tasks/${taskId}?adminId=${adminId}`);
        
        // Remove task from list
        tasks.value = tasks.value.filter(t => t.id !== taskId);
        
        // Refresh stats
        loadStats();
        
        showConfirmModal.value = false;
      } catch (error) {
        console.error('Error deleting task:', error);
        alert('Error deleting task: ' + (error.response?.data?.error || error.message));
      }
    };
    
    const confirmDeleteAchievement = (achievement) => {
      confirmModalTitle.value = 'Delete Achievement';
      confirmModalMessage.value = `Are you sure you want to delete the achievement "${achievement.title}"? This action cannot be undone.`;
      confirmAction.value = () => deleteAchievement(achievement.id);
      showConfirmModal.value = true;
    };
    
    const deleteAchievement = async (achievementId) => {
      try {
        const adminId = localStorage.getItem('user_id');
        await apiService.delete(`/admin/achievements/${achievementId}?adminId=${adminId}`);
        
        // Remove achievement from list
        achievements.value = achievements.value.filter(a => a.id !== achievementId);
        
        // Refresh stats
        loadStats();
        
        showConfirmModal.value = false;
      } catch (error) {
        console.error('Error deleting achievement:', error);
        alert('Error deleting achievement: ' + (error.response?.data?.error || error.message));
      }
    };
    
    const getUserName = (userId) => {
      const user = users.value.find(u => u.id === userId);
      return user ? user.name : `User #${userId}`;
    };
    
    const formatDate = (year, month, day) => {
      return `${year}-${String(month+1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
    };
    
    const formatDateTime = (dateTimeString) => {
      if (!dateTimeString) return '';
      const date = new Date(dateTimeString);
      return date.toLocaleString();
    };
    
    const getCategoryColor = (category) => {
      return categoryColors[category] || '#757575';
    };
    
    onMounted(() => {
      if (isAdmin.value) {
        loadStats();
        loadUsers();
        loadTasks();
        loadAchievements();
      }
    });
    
    return {
      isAdmin,
      activeTab,
      stats,
      users,
      userSearchQuery,
      filteredUsers,
      tasks,
      taskSearchQuery,
      filteredTasks,
      achievements,
      achievementSearchQuery,
      filteredAchievements,
      showAddUserModal,
      showEditUserModal,
      newUser,
      editingUser,
      showConfirmModal,
      confirmModalTitle,
      confirmModalMessage,
      confirmAction,
      loadStats,
      loadUsers,
      loadTasks,
      loadAchievements,
      addUser,
      editUser,
      updateUser,
      confirmDeleteUser,
      deleteUser,
      confirmDeleteTask,
      deleteTask,
      confirmDeleteAchievement,
      deleteAchievement,
      getUserName,
      formatDate,
      formatDateTime,
      getCategoryColor
    };
  }
};
  </script>
  
  <style scoped>
.admin-page {
  min-height: 100vh;
  padding: 2rem;
  color: var(--color-text);
}

.unauthorized-message {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  min-height: 60vh;
  gap: 1.5rem;
  animation: fadeIn 0.6s ease;
  max-width: 500px;
  margin: 0 auto;
  padding: 3rem 2rem;
  background: var(--color-card-bg);
  border-radius: 16px;
  box-shadow: var(--box-shadow);
  border: 1px solid var(--color-border);
}

.unauthorized-icon {
  font-size: 4rem;
  margin-bottom: 1rem;
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.1);
  }
  100% {
    transform: scale(1);
  }
}

.admin-container {
  max-width: 1280px;
  margin: 0 auto;
}

.admin-header {
  text-align: center;
  margin-bottom: 2.5rem;
  animation: fadeInDown 0.6s ease;
}

.admin-title {
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: 0.5rem;
  background: linear-gradient(135deg, var(--color-primary), var(--color-secondary));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.admin-subtitle {
  color: var(--color-text-secondary);
  font-size: 1.1rem;
}

/* Stats Cards */
.admin-stats {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1.5rem;
  margin-bottom: 2.5rem;
  animation: fadeInUp 0.8s ease;
}

.stat-card {
  background: var(--color-card-bg);
  border-radius: 16px;
  border: 1px solid var(--color-border);
  padding: 1.5rem;
  display: flex;
  align-items: center;
  gap: 1.5rem;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 15px 25px rgba(0, 0, 0, 0.1);
  border-color: var(--color-primary-light);
}

.stat-card::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 4px;
  background: linear-gradient(to right, var(--color-primary), var(--color-secondary));
  opacity: 0;
  transition: opacity 0.3s ease;
}

.stat-card:hover::after {
  opacity: 1;
}

.stat-icon-container {
  width: 60px;
  height: 60px;
  background: rgba(var(--color-primary-rgb), 0.1);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
}

.stat-card:hover .stat-icon-container {
  transform: scale(1.1) rotate(5deg);
  background: rgba(var(--color-primary-rgb), 0.2);
}

.stat-icon {
  font-size: 2rem;
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 2rem;
  font-weight: 600;
  color: var(--color-text);
  line-height: 1.2;
}

.stat-label {
  color: var(--color-text-secondary);
  font-size: 0.9rem;
}

/* Tabs */
.admin-tabs-container {
  background: var(--color-card-bg);
  border-radius: 16px;
  padding: 1.25rem;
  margin-bottom: 2rem;
  border: 1px solid var(--color-border);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.05);
  animation: fadeIn 1s ease;
}

.admin-tabs {
  display: flex;
  gap: 0.5rem;
  overflow-x: auto;
  scrollbar-width: none;
  -ms-overflow-style: none;
}

.admin-tabs::-webkit-scrollbar {
  display: none;
}

.tab-button {
  padding: 0.8rem 1.5rem;
  border: none;
  border-radius: 10px;
  background: transparent;
  color: var(--color-text-secondary);
  font-size: 1rem;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 0.75rem;
  white-space: nowrap;
}

.tab-button:hover {
  background: var(--color-card-bg-hover);
  color: var(--color-text);
  transform: translateY(-3px);
}

.tab-button.active {
  background: var(--color-primary);
  color: white;
  transform: translateY(-3px);
}

/* Content Sections */
.tab-content {
  background: var(--color-card-bg);
  border-radius: 16px;
  padding: 2rem;
  animation: fadeIn 0.5s ease;
  border: 1px solid var(--color-border);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.05);
}

.content-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
  padding-bottom: 1rem;
  border-bottom: 1px solid var(--color-border);
}

.content-header h2 {
  font-size: 1.5rem;
  color: var(--color-text);
  font-weight: 600;
}

.btn {
  padding: 0.7rem 1.2rem;
  border-radius: 10px;
  font-size: 0.9rem;
  font-weight: 500;
  transition: all 0.3s ease;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.btn-primary {
  background: var(--color-primary);
  color: white;
  box-shadow: 0 4px 10px rgba(var(--color-primary-rgb), 0.3);
}

.btn-primary:hover {
  background: var(--color-primary-dark);
  transform: translateY(-3px);
  box-shadow: 0 8px 15px rgba(var(--color-primary-rgb), 0.4);
}

.btn-cancel {
  background: transparent;
  border: 1px solid var(--color-border);
  color: var(--color-text-secondary);
}

.btn-cancel:hover {
  color: var(--color-text);
  background: var(--color-card-bg-hover);
  transform: translateY(-3px);
}

.btn-danger {
  background: #ff4757;
  color: white;
  box-shadow: 0 4px 10px rgba(255, 71, 87, 0.3);
}

.btn-danger:hover {
  background: #ff3040;
  transform: translateY(-3px);
  box-shadow: 0 8px 15px rgba(255, 71, 87, 0.4);
}

/* Search Filters */
.search-filter {
  margin-bottom: 2rem;
}

.search-box {
  position: relative;
  max-width: 400px;
}

.search-input {
  width: 100%;
  padding: 0.8rem 1rem 0.8rem 2.5rem;
  border-radius: 10px;
  border: 1px solid var(--color-border);
  background: var(--color-card-bg-hover);
  color: var(--color-text);
  font-size: 0.9rem;
  transition: all 0.3s ease;
}

.search-input:focus {
  outline: none;
  border-color: var(--color-primary);
  box-shadow: 0 0 0 3px rgba(var(--color-primary-rgb), 0.2);
  transform: translateY(-2px);
}

.search-icon {
  position: absolute;
  top: 50%;
  left: 0.8rem;
  transform: translateY(-50%);
  color: var(--color-text-secondary);
}

/* Tables */
.table-container {
  overflow-x: auto;
  border-radius: 10px;
  border: 1px solid var(--color-border);
  background: var(--color-card-bg-hover);
}

.data-table {
  width: 100%;
  border-collapse: collapse;
}

.data-table th {
  background: rgba(var(--color-primary-rgb), 0.1);
  color: var(--color-text);
  font-weight: 600;
  text-align: left;
  padding: 1rem;
  font-size: 0.9rem;
}

.data-table td {
  padding: 1rem;
  border-top: 1px solid var(--color-border);
  color: var(--color-text);
  font-size: 0.9rem;
}

.data-table tr {
  transition: all 0.2s ease;
}

.data-table tr:hover {
  background: rgba(var(--color-primary-rgb), 0.05);
}

.empty-message {
  text-align: center;
  padding: 2rem;
  color: var(--color-text-secondary);
  font-style: italic;
}

/* Badges */
.role-badge {
  display: inline-block;
  padding: 0.3rem 0.7rem;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 500;
  text-transform: uppercase;
}

.role-badge.admin {
  background-color: rgba(var(--color-primary-rgb), 0.15);
  color: var(--color-primary);
}

.role-badge.user {
  background-color: rgba(76, 175, 80, 0.15);
  color: #4CAF50;
}

.status-badge {
  display: inline-block;
  padding: 0.3rem 0.7rem;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 500;
}

.status-badge.completed {
  background-color: rgba(76, 175, 80, 0.15);
  color: #4CAF50;
}

.status-badge.pending {
  background-color: rgba(255, 152, 0, 0.15);
  color: #FF9800;
}

.category-badge {
  display: inline-block;
  padding: 0.3rem 0.7rem;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 500;
}

.priority-badge {
  display: inline-block;
  padding: 0.3rem 0.7rem;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 500;
}

.priority-badge.high {
  background-color: rgba(255, 71, 87, 0.15);
  color: #ff4757;
}

.priority-badge.medium {
  background-color: rgba(255, 152, 0, 0.15);
  color: #FF9800;
}

.priority-badge.low {
  background-color: rgba(76, 175, 80, 0.15);
  color: #4CAF50;
}

.points-badge {
  display: inline-block;
  padding: 0.3rem 0.7rem;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 600;
  background-color: rgba(var(--color-primary-rgb), 0.15);
  color: var(--color-primary);
}

/* Action buttons */
.actions-cell {
  display: flex;
  gap: 0.5rem;
}

.action-btn {
  width: 32px;
  height: 32px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: transparent;
  border: 1px solid var(--color-border);
  color: var(--color-text-secondary);
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
}

.action-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);
}

.action-btn.edit:hover {
  background-color: rgba(33, 150, 243, 0.1);
  color: #2196F3;
  border-color: #2196F3;
}

.action-btn.delete:hover {
  background-color: rgba(255, 71, 87, 0.1);
  color: #ff4757;
  border-color: #ff4757;
}

.action-btn .tooltip {
  position: absolute;
  bottom: 100%;
  left: 50%;
  transform: translateX(-50%);
  background: var(--color-card-bg);
  color: var(--color-text);
  padding: 0.4rem 0.6rem;
  border-radius: 6px;
  font-size: 0.75rem;
  white-space: nowrap;
  opacity: 0;
  pointer-events: none;
  transition: all 0.2s ease;
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);
  border: 1px solid var(--color-border);
  margin-bottom: 6px;
}

.action-btn:hover .tooltip {
  opacity: 1;
  transform: translateX(-50%) translateY(-5px);
}

/* Achievement styling */
.achievement-title-cell {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.achievement-icon {
  font-size: 1.25rem;
}

.points-cell {
  text-align: center;
}

/* Modal styling */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 1000;
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-backdrop {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.6);
  backdrop-filter: blur(5px);
  animation: fadeIn 0.3s ease;
}

.modal-content {
  position: relative;
  z-index: 1;
  background: var(--bg-gradient-start);
  border-radius: 16px;
  width: 90%;
  max-width: 500px;
  box-shadow: 0 25px 50px rgba(0, 0, 0, 0.25);
  overflow: hidden;
  border: 1px solid var(--color-border);
  animation: zoomIn 0.3s ease;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.5rem 2rem;
  border-bottom: 1px solid var(--color-border);
}

.modal-header h3 {
  font-size: 1.25rem;
  margin: 0;
  color: var(--color-text);
}

.close-btn {
  background: none;
  border: none;
  font-size: 1.5rem;
  color: var(--color-text-secondary);
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
}

.close-btn:hover {
  color: var(--color-text);
  transform: rotate(90deg);
}

.modal-body {
  padding: 2rem;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  padding: 1.5rem 2rem;
  border-top: 1px solid var(--color-border);
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  color: var(--color-text);
  font-size: 0.9rem;
  font-weight: 500;
}

.form-group input, 
.form-group select {
  width: 100%;
  padding: 0.8rem 1rem;
  border-radius: 10px;
  border: 1px solid var(--color-border);
  background: var(--color-card-bg-hover);
  color: var(--color-text);
  font-size: 0.9rem;
  transition: all 0.3s ease;
}

.form-group input:focus, 
.form-group select:focus {
  outline: none;
  border-color: var(--color-primary);
  box-shadow: 0 0 0 3px rgba(var(--color-primary-rgb), 0.2);
  transform: translateY(-2px);
}

/* Confirm modal styling */
.confirm-icon-container {
  display: flex;
  justify-content: center;
  margin-bottom: 1.5rem;
}

.confirm-icon {
  font-size: 3rem;
  animation: shake 0.5s ease;
}

.confirm-message {
  text-align: center;
  color: var(--color-text);
  font-size: 1.1rem;
  line-height: 1.6;
}

/* Animations */
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@keyframes fadeInDown {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
@keyframes zoomIn {
  from {
    transform: scale(0.8);
    opacity: 0;
  }
  to {
    transform: scale(1);
    opacity: 1;
  }
}
@keyframes shake {
  0% { transform: rotate(0deg); }
  25% { transform: rotate(10deg); }
  50% { transform: rotate(-10deg); }
  75% { transform: rotate(10deg); }
  100% { transform: rotate(0deg); }
}
  </style>