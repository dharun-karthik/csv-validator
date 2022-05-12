package utils

object EnvVars {
    fun setTestDbEnvVars() {
        setEnvVars("DB_NAME", "h2")
        setEnvVars("DB_USERNAME", "db_user")
        setEnvVars("DB_PASSWORD", "database-user")
    }

    private fun setEnvVars(key: String, value: String) {
        val env = System.getenv()
        val cl = env.javaClass
        val field = cl.getDeclaredField("m")
        field.isAccessible = true
        val writableEnv = field.get(env) as MutableMap<String, String>
        writableEnv[key] = value
    }
}