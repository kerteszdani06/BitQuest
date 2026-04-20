# BitQuest

BitQuest is an Android fantasy guild-management game built in Java.  
The player recruits heroes, trains them, forms a dungeon party, battles monsters, and tracks long-term guild progress through saved data and statistics.

## Overview

The idea of the project is to combine simple RPG combat with party management.  
Instead of controlling a single character, the player builds a guild, improves heroes over time, and decides who should be sent into battle. Heroes can gain experience, record victories, get defeated, and recover in the medbay before returning to service.

The application is designed as a multi-screen Android app with a clear separation between UI, game logic, data models, and utility classes.

## Main Features

- Recruit new heroes with a custom name
- Choose from multiple hero classes
- Store all heroes inside a guild archive
- Train heroes to gain experience and increase their progress
- Build a dungeon team with a lead hero and reserves
- Choose a mission type before entering combat
- Fight monsters using multiple combat actions:
  - Attack
  - Swap with reserve heroes
  - Use potion
  - Defend
  - Use special ability
- Send defeated heroes to medbay
- Recover medbay heroes over time
- Track hero performance with statistics
- Display training progress in a pie chart
- Save and load guild data automatically
- Save and load guild data from file manually

## Hero Classes

The project currently includes the following hero classes:

- Knight
- Archer
- Cleric
- Wizard
- Necromancer

Each hero class extends the common `Hero` base class and follows the same core progression system while keeping its own combat identity.

## Gameplay Flow

1. The player starts from the main screen and sees the current guild roster.
2. New heroes can be recruited from the recruit screen.
3. Existing heroes can be trained to gain experience.
4. The player selects a dungeon team from available heroes.
5. A mission type is chosen before starting the dungeon.
6. In battle, the player can attack, defend, swap heroes, use potions, or trigger a special ability.
7. If heroes survive, they gain rewards such as experience and victories.
8. If heroes fall, they are moved to the medbay and recover later.
9. The statistics screen shows progress such as missions, wins, losses, and training sessions.

## Screens

The app contains the following main screens:

- **MainActivity** – main hub and guild overview
- **RecruitHeroActivity** – create and add a new hero
- **TrainingGroundsActivity** – train an existing hero
- **SelectDuoActivity** – choose the party before entering the dungeon
- **DungeonGateActivity** – handle the full combat sequence
- **StatisticsActivity** – display hero statistics and chart-based training data

## Progression and Combat

Heroes improve over time through training and dungeon participation.  
Training increases experience, and once enough experience is collected, the hero’s skill level improves.

During dungeon combat, the player manages a small party rather than a single unit. The lead hero fights directly, while reserve heroes can be swapped in when needed. This creates a more tactical gameplay loop and makes party composition important.

Combat also includes support systems such as potions and special abilities. In addition, defeated heroes are not simply removed from the game; instead, they are sent to the medbay, which adds a longer-term management element to the app.

## Statistics System

The statistics screen provides both text-based and chart-based feedback about guild progress.  
For each hero, the app tracks values such as:

- missions played
- victories
- lost missions
- training sessions

Training activity is also visualized using a pie chart, which gives the user a quick summary of how active each hero has been.

## Data Persistence

BitQuest stores data in two ways:

### 1. Automatic Save
The application uses `SharedPreferences` together with Gson serialization to automatically save and load the guild archive.

### 2. Manual File Save / Load
The app also supports saving and loading data through an internal JSON file:

- `crew_data.json`

This gives the project an extra layer of persistence beyond normal in-app memory.

## Project Structure

```text
app/src/main/java/com/example/bitquest
│
├── MainActivity.java
├── logic
│   ├── CombatManager.java
│   ├── HeroTypeAdapter.java
│   └── SaveManager.java
│
├── model
│   ├── Archer.java
│   ├── Cleric.java
│   ├── GuildArchive.java
│   ├── Hero.java
│   ├── Knight.java
│   ├── Monster.java
│   ├── Necromancer.java
│   └── Wizard.java
│
├── ui
│   ├── DungeonGateActivity.java
│   ├── MedbayFragment.java
│   ├── RecruitHeroActivity.java
│   ├── RosterFragment.java
│   ├── SelectDuoActivity.java
│   ├── StatisticsActivity.java
│   ├── TrainingGroundsActivity.java
│   └── adapter
│
└── util
    └── IdGenerator.java
