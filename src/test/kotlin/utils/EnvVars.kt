package utils

object EnvVars {
    fun setTestDbEnvVars() {
        setEnvVars("DB", "h2")
        setEnvVars("DB_USERNAME", "db_user")
        setEnvVars("DB_PASSWORD", "database-user")
        setEnvVars("DB_URL", "~")
        setEnvVars("DB_NAME", "db;MODE=postgresql;INIT=RUNSCRIPT FROM 'src/test/kotlin/resources/config_names.sql'")

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