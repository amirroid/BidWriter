package ir.amirroid.bidwriter

val prompt = """
Write a freelance bid proposal using the following inputs and these exact formatting rules. You must follow all writing style rules strictly.

Inputs:
1. Client's Name: [Insert name]
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
1. Greet the client and mention their name on the same line, following the selected tone. For example, say “Hi [Name]” for Friendly tone. **Never add a line break after the greeting. Always keep the greeting and the next sentence on the same line.**
2. Immediately confirm that I can do the project. Then, briefly describe it in 1–2 sentences based on the client's description. **Do not repeat all the project details. Instead, say something like: “I can build this for you — a notes app with voice recording and syncing features.” Mention key features if they are clearly stated, but avoid repeating the whole description.**
3. If suggestions are provided, write them in a smooth, professional paragraph. (Skip this section if suggestions are empty.)
4. Add the short paragraph about me above, and connect it naturally. Mention only the major skills that are directly relevant to the project (e.g., database, API integration, UI design) and say that I have worked with them before and can confidently handle them. Keep it short and simple, without technical details.
5. If portfolio items are provided, refer to them clearly and show how they relate to the project.
6. If questions are given, list them at the end in numbered format (1., 2., 3.). If no questions, skip this section.
7. Close the message by saying: “Feel free to message me so we can get started.” If a deadline is given, mention it at the end.

Writing style rules:
- Avoid unnecessary new lines.
- Never press Enter after the greeting — the greeting and next sentence must stay on the same line.
- Use a flowing, continuous paragraph unless you must separate clearly (e.g., a numbered list).
- Avoid repeating the project description in detail — just summarize the core task with key features.
- Never use double dashes (--) in the text.
- Keep the tone human, helpful, and easy to read.

Important:
Always provide **two separate sections** in the response:

### English Version  
[write the full English proposal here based on the rules above]  


---


### Persian Version  
[write the full Persian proposal here based on the same structure and rules]  


Both versions must fully follow the formatting and style rules. Make sure the Persian version also avoids unnecessary line breaks and keeps the greeting and first sentence on the same line.

In my next messages, try to revise or improve the bid text based on updated inputs. Don’t rebuild the whole structure unless I ask — just update the content and tone while keeping the format consistent.
""".trimIndent()