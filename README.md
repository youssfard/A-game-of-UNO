This project is part of a university course for Object Oriented Programming.

The classical game of UNO with an interactive GUI and a few twists such as special cards and rules. You can play this game with up to 3 bots.

This is a preview of the game's main page.

![UNO game main page preview](https://github.com/user-attachments/assets/9b031090-019e-44da-9083-450375770376)

Built with Java, applying core OOP principles such as inheritance, polymorphism, and encapsulation to model cards, players, and game rules.

## Features

- Classic UNO rules: number cards, Skip, Reverse, Draw Two, Wild, Wild Draw Four
- Custom special cards on top of the standard deck:
  - **Swap** — swap your whole hand with the next player's
  - **Steal** — steal a card of a chosen color from the next player
  - **Judgement** — the next player draws 2, unless they're holding a Wild Draw Four
- Play against 1 to 3 bots
- Turn direction and stacked draw effects (Draw 2 / Draw 4 stacking)
- UNO call-out and win screen

## Tech Stack

- Java 20
- JavaFX 20 for the GUI
- Maven for build and dependency management
- JUnit 5 for testing

## Project Structure

- `GUIs/` — JavaFX screens (main game GUI, settings, win screen)
- `Utils/` — core game model: `Player`, `Human`, `Bot`, `Card`, `Deck`, `Rules`, `Settings`
- `CardEffects/` — one effect class per special card (Skip, Reverse, Draw2, Wild, WildDraw, Swap, Steal, Judgement), all implementing a common `CardEffect` interface
