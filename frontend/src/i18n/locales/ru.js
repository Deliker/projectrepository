export default {
    // General
    appName: 'TaskMaster',

    // Navigation
    nav: {
        calendar: 'Календарь',
        achievements: 'Достижения',
        about: 'О нас',
        login: 'Вход',
        signup: 'Регистрация',
        logout: 'Выход',
        admin: 'Панель администратора'
    },

    // Home Page
    home: {
        hero: {
            titleStart: 'Управляйте своим временем,',
            titleAccent: 'Достигайте большего',
            subtitle: 'Упорядочите свои задачи, отслеживайте достижения и повышайте продуктивность с помощью нашей интуитивно понятной системы управления задачами',
            getStarted: 'Начать',
            startNow: 'Начать сейчас — Бесплатно'
        },
        features: {
            title: 'Почему выбирают TaskMaster?',
            subtitle: 'Мощные функции для повышения вашей продуктивности и помощи в достижении большего каждый день',
            smartPlanning: 'Умное планирование',
            smartPlanningDesc: 'Организуйте и управляйте своими задачами с помощью интуитивно понятного календарного интерфейса. Планируйте свой день, неделю или месяц с легкостью.',
            timeOptimization: 'Оптимизация времени',
            timeOptimizationDesc: 'Структурируйте свое время эффективно с помощью инструментов управления задачами. Расставляйте приоритеты и фокусируйтесь на самом важном.',
            taskTracking: 'Отслеживание задач',
            taskTrackingDesc: 'Контролируйте свой прогресс и будьте в курсе ежедневных задач. Никогда не пропустите дедлайн или важное обязательство.',
            achievementSystem: 'Система достижений',
            achievementSystemDesc: 'Разблокируйте достижения по мере повышения продуктивности. Отслеживайте свой путь и сохраняйте мотивацию с наградами.'
        },
        cta: {
            title: 'Готовы оптимизировать свое время?',
            subtitle: 'Присоединяйтесь к тысячам пользователей, которые преобразили свою повседневную продуктивность с TaskMaster'
        }
    },

    // Calendar Page
    calendar: {
        title: 'Ваш Календарь',
        subtitle: 'Организуйте задачи и повышайте продуктивность',
        addTask: 'Добавить задачу',
        filter: 'Фильтр',
        searchTasks: 'Поиск задач...',
        clearFilters: 'Очистить фильтры',
        categories: 'Категории',
        priority: 'Приоритет',
        high: 'Высокий',
        medium: 'Средний',
        low: 'Низкий',
        month: {
            january: 'Январь',
            february: 'Февраль',
            march: 'Март',
            april: 'Апрель',
            may: 'Май',
            june: 'Июнь',
            july: 'Июль',
            august: 'Август',
            september: 'Сентябрь',
            october: 'Октябрь',
            november: 'Ноябрь',
            december: 'Декабрь'
        },
        weekdays: {
            mon: 'Пн',
            tue: 'Вт',
            wed: 'Ср',
            thu: 'Чт',
            fri: 'Пт',
            sat: 'Сб',
            sun: 'Вс'
        },
        categoryNames: {
            work: 'Работа',
            personal: 'Личное',
            study: 'Учеба',
            health: 'Здоровье',
            shopping: 'Покупки',
            other: 'Другое'
        },
        modal: {
            addTask: 'Добавить задачу',
            editTask: 'Редактировать задачу',
            date: 'Дата:',
            taskDescription: 'Описание задачи:',
            whatToDo: 'Что нужно сделать?',
            category: 'Категория:',
            saveTask: 'Сохранить задачу',
            saveChanges: 'Сохранить изменения',
            noTasks: 'Нет задач на этот день',
            confirmDelete: 'Вы уверены, что хотите удалить эту задачу?'
        },
        achievements: {
            unlocked: 'Достижение разблокировано!',
            beginner: 'Первый шаг (Выполните свою первую задачу)',
            productiveDay: 'Продуктивный день (Выполните 5 задач за один день)',
            earlyBird: 'Ранняя птичка (Выполните задачу до 6 утра)',
            nightOwl: 'Ночная сова (Выполните задачу после полуночи)',
            goalOriented: 'Целеустремленность (Выполните 10 задач за неделю)',
            weekendWarrior: 'Выходной воин (Выполните 5 задач в выходные)',
            perfectWeek: 'Идеальная неделя (Выполняйте хотя бы 1 задачу каждый день недели)',
            projectLeader: 'Руководитель проекта (Выполните все задачи за месяц)',
            timeMaster: 'Мастер времени (Выполните 50 задач)',
            consistency: 'Король постоянства (Выполняйте задачи 30 дней подряд)',
            earlyPlanner: 'Планировщик (Спланируйте задачи на неделю вперед)',
            speedRunner: 'Скоростной бегун (Выполните 3 задачи за час)',
            multitasker: 'Многозадачность (Выполните задачи в 5 разных категориях)'
        }
    },

    // Achievements Page
    achievements: {
        title: 'Ваши Достижения',
        subtitle: 'Отслеживайте свой путь к продуктивности и разблокируйте награды',
        stats: {
            unlocked: 'Разблокировано достижений'
        },
        search: 'Поиск достижений...',
        filters: {
            all: 'Все',
            unlocked: 'Разблокированные',
            locked: 'Заблокированные'
        },
        categories: {
            daily: 'Ежедневное совершенство',
            weekly: 'Еженедельные вехи',
            monthly: 'Ежемесячное мастерство',
            special: 'Особые достижения'
        },
        status: {
            unlocked: 'Разблокировано',
            locked: 'Заблокировано'
        },
        empty: {
            title: 'Достижения не найдены',
            subtitle: 'Попробуйте изменить параметры поиска или фильтры',
            reset: 'Сбросить фильтры'
        }
    },

    // Auth Page
    auth: {
        login: {
            title: 'С возвращением',
            subtitle: 'Войдите в аккаунт, чтобы продолжить',
            email: 'Электронная почта',
            password: 'Пароль',
            rememberMe: 'Запомнить меня',
            forgotPassword: 'Забыли пароль?',
            noAccount: 'Нет аккаунта?',
            signUp: 'Зарегистрироваться',
            continueWith: 'или продолжить с помощью'
        },
        register: {
            title: 'Создать аккаунт',
            subtitle: 'Присоединяйтесь к TaskMaster, чтобы повысить продуктивность',
            fullName: 'Полное имя',
            email: 'Электронная почта',
            password: 'Пароль',
            confirmPassword: 'Подтвердите пароль',
            createPassword: 'Создайте пароль',
            confirmYourPassword: 'Подтвердите ваш пароль',
            agreeTerms: 'Я согласен с',
            termsService: 'Условиями использования',
            and: 'и',
            privacyPolicy: 'Политикой конфиденциальности',
            alreadyAccount: 'Уже есть аккаунт?',
            logIn: 'Войти',
            signUpWith: 'или зарегистрироваться с помощью'
        },
        validation: {
            required: '{field} обязателен',
            validEmail: 'Пожалуйста, введите действительный адрес электронной почты',
            minLength: '{field} должен содержать не менее {length} символов',
            passwordsMatch: 'Пароли не совпадают',
            weakPassword: 'Пароль слишком слабый',
            agreeTerms: 'Вы должны согласиться с Условиями и Политикой конфиденциальности'
        },
        passwordStrength: {
            weak: 'Слабый',
            fair: 'Нормальный',
            good: 'Хороший',
            strong: 'Сильный',
            veryStrong: 'Очень сильный'
        }
    },

    about: {
        titleStart: 'О нас',
        titleAccent: 'TaskMaster',
        description: 'TaskMaster - это мощный инструмент управления задачами, призванный помочь вам оптимизировать свое время и повысить продуктивность. Благодаря таким функциям, как интеллектуальное планирование, отслеживание задач и система достижений, TaskMaster позволяет вам взять под контроль свои задачи и добиваться большего каждый день. Независимо от того, управляете ли вы личными проектами или профессиональными обязательствами, TaskMaster поможет вам на пути к успеху.',
        OurTeam: 'Знакомство с командой',
        team1: 'Данил Макаров - моральная поддержка',
        team2: 'Дениска Устинович - Fullstack-разработчик',
        team3: 'Роландс Калниньш - дизайнер',
        team: {
            subtitle: 'Мы - команда увлеченных людей, стремящихся помочь вам достичь ваших целей. Наша миссия - предоставить вам лучшие инструменты и ресурсы для оптимизации вашего времени и повышения продуктивности.',
            text1: "У него большой опыт в оказании моральной помощи. Без моральной поддержки наша команда не справилась бы.",
            text2: '10 лет опыта, самый ценный актив в любой команде. Также умеет готовить.',
            text3: "Нечего сказать, просто он лучший."
        },
        cta: {
            title: 'Хотите попробовать?',
            subtitle: 'Тогда присоединяйтесь к нам и начните свой путь к продуктивности уже сегодня!',
        },
    },

    //admin Page
    admin: {
        title: 'Административная панель',

        subtitle: 'Управление пользователями, задачами и достижениями',
        search: 'Поиск...',
        actions: 'Действия',
        tabs: {
            users: 'Пользователи',
            tasks: 'Задачи',
            achievements: 'Достижения',
        },
        stats: {
            totalUsers: 'Общее количество пользователей',
            totalTasks: 'Общее количество задач',
            totalAchievements: 'Общее количество достижений',
        },
        users: {
            title: 'Управление пользователями',
            addUser: 'Добавить пользователя',
            name: 'Имя',
            email: 'Электронная почта',
            role: 'Роль',
            edit: 'Редактировать',
            delete: 'Удалить',
            noUsers: 'Пользователи не найдены',
        },
        tasks: {
            title: 'Управление задачами',
            description: 'Описание',
            category: 'Категория',
            priority: 'Приоритет',
            date: 'Дата',
            status: 'Статус',
            user: 'Пользователь',
            edit: 'Редактировать',
            delete: 'Удалить',
            noTasks: 'Нет задач',
        },
        achievements: {
            title: 'Управление достижениями',
            description: 'Описание',
            category: 'Категория',
            points: 'Очки',
            user: 'Пользователь',
            unlockDate: 'Дата разблокировки',
            edit: 'Редактировать',
            delete: 'Удалить',
            noAchievements: 'Нет достижений',
        },
    },

    // Language Selection
    language: {
        select: 'Язык',
        en: 'English',
        ru: 'Русский',
        lv: 'Latviešu'
    }
};