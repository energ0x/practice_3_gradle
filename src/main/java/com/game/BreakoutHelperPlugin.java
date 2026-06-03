package com.game;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskExecutionException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

public class BreakoutHelperPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {

        project.getTasks().register("checkGameAssets", task -> {
            task.setGroup("breakout");
            task.setDescription("Verifies that critical game assets are present.");

            task.doLast(t -> {
                String[] requiredFiles = {
                        "img/breakout.png", "img/background.jpeg",
                        "img/exit.png", "img/exit_pressed.png",
                        "img/heart.png", "img/init.gif",
                        "img/lives_less_en.PNG", "img/lost.gif",
                        "img/lost_en.JPEG", "img/play.png",
                        "img/play_pressed.png", "img/ready_en.PNG",
                        "img/win.gif", "img/win_en.JPEG",
                        "music/menu_theme.wav", "music/start.wav",
                        "music/bounce.wav", "music/bounce_brick.wav",
                        "music/click_1.wav", "music/click_2.wav",
                        "music/exit.wav", "music/gameplay_music.wav",
                        "music/intro_excellent.wav", "music/lost.wav",
                        "music/win.wav", "music/warning.wav"
                };

                boolean allFound = true;
                for (String path : requiredFiles) {
                    if (!project.file(path).exists()) {
                        System.err.println("Missing critical asset: " + path);
                        allFound = false;
                    }
                }

                if (allFound) {
                    System.out.println("All critical assets verified successfully!");
                } else {
                    throw new RuntimeException("Game assets are missing. Build failed.");
                }
            });
        });

        project.getTasks().register("generateGameInfo", task -> {
            task.setGroup("breakout");
            task.setDescription("Generates a build info file with version and timestamp.");

            task.doLast(t -> {
                File buildDir = project.getLayout().getBuildDirectory().get().getAsFile();
                buildDir.mkdirs();
                File infoFile = new File(buildDir, "build-info.txt");

                try (FileWriter writer = new FileWriter(infoFile)) {
                    writer.write("Game: Breakout\n");
                    writer.write("Version: " + project.getVersion() + "\n");
                    writer.write("Build Time: " + Instant.now() + "\n");
                    System.out.println("Build info generated at: " + infoFile.getAbsolutePath());
                } catch (IOException e) {
                    throw new RuntimeException("Failed to write build info", e);
                }
            });
        });
    }
}