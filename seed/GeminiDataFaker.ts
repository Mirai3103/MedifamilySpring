import {
  ChatSession,
  GenerativeModel,
  GoogleGenerativeAI,
} from "@google/generative-ai";
import fs from "fs";
export class GeminiDataFaker {
  private model: GenerativeModel;
  private chat?: ChatSession;

  constructor(apiKey: string) {
    const genAI = new GoogleGenerativeAI(apiKey);
    this.model = genAI.getGenerativeModel({ model: "gemini-2.0-flash" });
    this.initChat();
  }

  public async initChat() {
    this.chat = this.model.startChat({
      history: [
        {
          role: "user",
          parts: [{ text: this.getPrompt() }],
        },
      ],
    });
  }

  async generate<T extends string | number | symbol>(
    schema: Record<T, string>,
    description?: string
  ): Promise<Record<T, any>> {
    const result = await this.chat!.sendMessage(
      description + "\n" + JSON.stringify(schema)
    );
    const text = result.response.text();

    try {
      const json = text.replace(/```json/g, "").replace(/```/g, "");
      return JSON.parse(json);
    } catch (err) {
      throw new Error("Gemini trả về sai định dạng JSON: " + err);
    }
  }

  private getPrompt(): string {
    const prompt = fs.readFileSync("prompts/gemini.txt", "utf-8");
    return prompt;
  }
}
