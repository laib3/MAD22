package it.polito.mainactivity.model

data class Skill(val category: String) {
    var description: String = ""
    var active: Boolean = false

    constructor(category: String, description: String, active: Boolean) : this(category) {
        this.description = description
        this.active = active
    }

    constructor(skillMap: HashMap<String, Any>): this(skillMap.get("category") as String){
        this.description = skillMap["description"] as String
        this.active = (skillMap["active"] as String) == "true"
    }

    override fun toString() =
        """{ "category": "$category", "description": "$description", "active": $active }"""

    fun toMap(): HashMap<String, Any>{
        return hashMapOf(
            "category" to category,
            "description" to description,
            "active" to active
        )
    }
}