package ir.amirroid.bidwriter

val prompt = """
    Write a freelance bid proposal using the following inputs and these exact formatting rules.

    Inputs:
    1. Client's Name: [Insert name]
    2. Language: [English or Persian]
    3. Tone: [Friendly, Formal, or Neutral]
    4. Project Description: [Paste full project text]
    5. My Suggestions: [Optional – list of improvement ideas or solutions] — if empty, skip this part
    6. Portfolio Items: [Optional – list of relevant works, links or short descriptions] — if empty, skip this part
    7. My Questions: [Optional – list of questions as bullet points or numbers] — if none, skip this part
    8. Deadline: [Optional – time or date for project delivery]
    9. Formatting Instructions: [Optional – detailed instructions about how to write or format the proposal; if provided, strictly follow these instructions; otherwise, use default formatting rules below]

    My background (always include this in the relevant part):
    My name is Amirreza Gholami. I’ve been programming for over 5 years, with a strong interest in modern development practices. I always try to write clean, maintainable code. My core skills include Android, Jetpack Compose, Room, Ktor, Retrofit, Koin, and Coil. I’m experienced in building apps that involve APIs, databases, hardware integrations, media (camera, audio, video), alarms, sensors, and advertising.

    Default Formatting & structure rules (only use if Formatting Instructions are not provided):
    1. Greet the client and mention their name on the same line, following the selected tone. For example, say “Hi [Name]” for Friendly tone.
    2. Immediately say that I will do the project, and briefly summarize the task in 1 or 2 sentences to show I’ve read it carefully — do not say “From what I understand,” just state it directly.
    3. If suggestions are provided, list them clearly in a smooth and well-written paragraph (don’t write this section if it’s empty).
    4. Add the paragraph about me above, and connect it naturally. If the project matches my skills, say so and explain why I’m a good fit.
    5. If portfolio items are provided, refer to them clearly and mention how they relate to the project.
    6. If questions are given, list them at the end in numbered format (1., 2., 3.). If no questions, skip this section.
    7. Close the message by saying “Feel free to message me so we can get started.” If a delivery time is provided, mention it at the end.

    Writing style rules (apply only if Formatting Instructions are not provided):
    - Avoid unnecessary new lines
    - Keep the text flowing unless it’s a logically separate section
    - Keep it human, helpful, and easy to read

    Important:
    Always provide two versions of the proposal: first in English, then in Persian. Each version should independently follow all the above rules or the Formatting Instructions if provided.

    In my next messages, try to revise or improve the bid text based on updated inputs. Don’t rebuild the whole structure unless I ask — just update the content and tone while keeping the format consistent.

""".trimIndent()