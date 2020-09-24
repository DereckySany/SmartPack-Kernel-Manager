/*
 * Copyright (C) 2020-2021 sunilpaulmathew <sunil.kde@gmail.com>
 *
 * This file is part of SmartPack Kernel Manager, which is a heavily modified version of Kernel Adiutor,
 * originally developed by Willi Ye <williye97@gmail.com>
 *
 * Both SmartPack Kernel Manager & Kernel Adiutor are free softwares: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SmartPack Kernel Manager is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SmartPack Kernel Manager.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.smartpack.kernelmanager.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.smartpack.kernelmanager.R;
import com.smartpack.kernelmanager.utils.root.RootUtils;

/**
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on September 23, 2020
 */

public class TerminalActivity extends BaseActivity {

    private static AppCompatEditText mShellCommand;
    private static AppCompatEditText mShellOutput;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal);

        AppCompatImageButton mBack = findViewById(R.id.back_button);
        AppCompatImageButton mSave = findViewById(R.id.enter_button);
        mBack.setOnClickListener(v -> onBackPressed());
        mShellCommand = findViewById(R.id.shell_command);
        AppCompatTextView mShellCommandTitle = findViewById(R.id.shell_command_title);
        mShellOutput = findViewById(R.id.shell_output);
        mShellCommandTitle.setText("<Root> ");
        mSave.setOnClickListener(v -> runCommand());
        AppCompatTextView mClearAll = findViewById(R.id.clear_all);
        mClearAll.setOnClickListener(v -> {
            clearAll();
        });
    }

    @SuppressLint("SetTextI18n")
    private void runCommand() {
        if (mShellCommand.getText() != null) {
            if (mShellCommand.getText() != null && !mShellCommand.getText().toString().isEmpty()) {
                if (mShellCommand.getText().toString().equals("clear")) {
                    clearAll();
                } else if (mShellCommand.getText().toString().equals("exit")) {
                    onBackPressed();
                } else if (mShellCommand.getText().toString().equals("su") || mShellCommand.getText().toString().contains("su ")) {
                    mShellCommand.setText(null);
                } else {
                    String mResult = "<Root> " + mShellCommand.getText().toString() + "\n" + RootUtils.runAndGetError(mShellCommand.getText().toString());
                    if (mResult.equals("<Root> " + mShellCommand.getText().toString() + "\n")) {
                        mResult = "<Root> " + mShellCommand.getText().toString() + "\n" + mShellCommand.getText().toString();
                    }
                    mShellCommand.setText(null);
                    mShellOutput.setText(mShellOutput.getText() + "\n\n" + mResult);
                    mShellOutput.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private static void clearAll() {
        mShellOutput.setText(null);
        mShellOutput.setVisibility(View.GONE);
        mShellCommand.setText(null);
    }

}