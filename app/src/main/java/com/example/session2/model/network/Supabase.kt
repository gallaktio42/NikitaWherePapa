package com.example.session2.model.network

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object Supabase {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://xasbnamubwidfapyanxc.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inhhc2JuYW11YndpZGZhcHlhbnhjIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzAxMTQwNzAsImV4cCI6MjA0NTY5MDA3MH0.PpwKJGdxacwGZnBroF0z8tQgaGU4EhEp7oOKBymtDaQ"
    ) {
        install(Auth)
        install(Postgrest)
    }
}